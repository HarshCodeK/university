"""
Real-time Face Mask Detection using OpenCV and a pre-trained model.
Detects faces via DNN, then classifies each face as Mask / No Mask.
Press 'q' to quit the webcam feed.
"""

import cv2
import numpy as np
from tensorflow.keras.models import load_model

# Configuration
CONFIDENCE_THRESHOLD = 0.5  # Minimum confidence for face detection
MODEL_PATH = "model/mask_detector.model"
PROTOTXT_PATH = "model/deploy.prototxt"
CAFFE_PATH = "model/res10_300x300_ssd_iter_140000.caffemodel"

def load_models():
    """Load face detector (Caffe) and mask classifier (Keras)."""
    print("[INFO] Loading face detector...")
    face_net = cv2.dnn.readNet(PROTOTXT_PATH, CAFFE_PATH)

    print("[INFO] Loading mask classifier...")
    mask_model = load_model(MODEL_PATH)

    return face_net, mask_model

def detect_and_predict_mask(frame, face_net, mask_model):
    """Detect faces and classify mask status for each face."""
    h, w = frame.shape[:2]
    blob = cv2.dnn.blobFromImage(frame, 1.0, (300, 300), (104.0, 177.0, 123.0))

    face_net.setInput(blob)
    detections = face_net.forward()

    faces = []
    locs = []
    preds = []

    for i in range(0, detections.shape[2]):
        confidence = detections[0, 0, i, 2]
        if confidence > CONFIDENCE_THRESHOLD:
            box = detections[0, 0, i, 3:7] * np.array([w, h, w, h])
            (x1, y1, x2, y2) = box.astype("int")
            x1, y1 = max(0, x1), max(0, y1)
            x2, y2 = min(w - 1, x2), min(h - 1, y2)

            face = frame[y1:y2, x1:x2]
            if face.shape[0] < 20 or face.shape[1] < 20:
                continue

            # Preprocess for mask model
            face_rgb = cv2.cvtColor(face, cv2.COLOR_BGR2RGB)
            face_resized = cv2.resize(face_rgb, (224, 224))
            face_normalized = face_resized / 255.0
            face_array = np.expand_dims(face_normalized, axis=0)

            # Predict: output is [mask_prob, no_mask_prob]
            mask_pred = mask_model.predict(face_array, verbose=0)[0]
            (mask_prob, without_mask_prob) = mask_pred

            label = "Mask" if mask_prob > without_mask_prob else "No Mask"
            confidence_val = max(mask_prob, without_mask_prob) * 100

            locs.append((x1, y1, x2, y2))
            preds.append((label, confidence_val))

    return locs, preds

def main():
    print("[INFO] Starting Face Mask Detection...")
    face_net, mask_model = load_models()

    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        print("[ERROR] Could not open webcam.")
        return

    print("[INFO] Webcam opened. Press 'q' to quit.")

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        locs, preds = detect_and_predict_mask(frame, face_net, mask_model)

        for (x1, y1, x2, y2), (label, confidence) in zip(locs, preds):
            color = (0, 255, 0) if label == "Mask" else (0, 0, 255)
            cv2.rectangle(frame, (x1, y1), (x2, y2), color, 2)
            cv2.putText(frame, f"{label}: {confidence:.1f}%", (x1, y1 - 10),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.6, color, 2)

        cv2.imshow("Face Mask Detection", frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()
    print("[INFO] Application closed.")

if __name__ == "__main__":
    main()
