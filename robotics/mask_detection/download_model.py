"""
Downloads the pre-trained face mask detection model.
Uses a lightweight MobileNetV2 model fine-tuned for mask classification.
"""
import os
import urllib.request
import zipfile

MODEL_DIR = "model"
MODEL_URL = "https://github.com/chandrikadeb7/Face-Mask-Detection/raw/master/model/mask_detector.model"
FACE_PROTO = "https://raw.githubusercontent.com/opencv/opencv/master/samples/dnn/face_detector/deploy.prototxt"
FACE_CAFFE = "https://raw.githubusercontent.com/opencv/opencv_3rdparty/dnn_samples_face_detector_20170830/res10_300x300_ssd_iter_140000.caffemodel"

os.makedirs(MODEL_DIR, exist_ok=True)

files = {
    f"{MODEL_DIR}/mask_detector.model": MODEL_URL,
    f"{MODEL_DIR}/deploy.prototxt": FACE_PROTO,
    f"{MODEL_DIR}/res10_300x300_ssd_iter_140000.caffemodel": FACE_CAFFE,
}

print("Downloading model files...")
for path, url in files.items():
    if not os.path.exists(path):
        print(f"Downloading {url.split('/')[-1]}...")
        urllib.request.urlretrieve(url, path)
        print(f"Saved to {path}")
    else:
        print(f"{path} already exists")

print("All model files downloaded successfully!")
