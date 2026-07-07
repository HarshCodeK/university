# Real-time Face Mask Detection

A computer vision project that detects whether a person is wearing a face mask in real-time using webcam feed.

## How It Works

1. **Face Detection**: Uses OpenCV's DNN module with a pre-trained Caffe SSD model (ResNet-10) to detect faces in the frame
2. **Mask Classification**: Each detected face is cropped, resized to 224x224, and passed through a fine-tuned MobileNetV2 model that classifies it as "Mask" or "No Mask"
3. **Visual Output**: Green bounding box with "Mask Detected" for masked faces, red box with "No Mask" for unmasked faces, along with confidence percentage

## Requirements

- Python 3.8+
- Webcam

## How to Run

```bash
# 1. Install dependencies
pip install -r requirements.txt

# 2. Download pre-trained model files
python download_model.py

# 3. Run the detector
python mask_detector.py

# Press 'q' to quit
```

## Limitations

- Model works best in good lighting conditions
- May struggle with partially visible faces or extreme angles
- Pre-trained model was trained on standard face mask types (surgical, N95)
- Performance depends on your hardware (GPU recommended for real-time)

## Project Structure

```
mask_detection/
├── mask_detector.py        # Main detection script
├── download_model.py       # Downloads pre-trained models
├── requirements.txt        # Python dependencies
├── model/                  # Downloaded model files (auto-generated)
└── README.md
```
