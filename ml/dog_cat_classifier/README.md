# Dog vs Cat Image Classifier

A Machine Learning web application that classifies uploaded images as "Dog" or "Cat" using a pre-trained MobileNetV2 model.

## How It Works

1. User uploads an image via the Streamlit interface
2. Image is resized to 224x224 and preprocessed for MobileNetV2
3. Pre-trained MobileNetV2 (trained on ImageNet) predicts the class
4. Predictions are mapped to "Dog" or "Cat" with confidence percentage

## How to Run

```bash
# 1. Install dependencies
pip install -r requirements.txt

# 2. Run the app
streamlit run app.py

# 3. Open browser at http://localhost:8501
```

## Model

- **Architecture**: MobileNetV2
- **Training**: Pre-trained on ImageNet (1000 classes including dog and cat breeds)
- **Approach**: Dog/cat breeds are grouped into binary classification

## Project Structure

```
dog_cat_classifier/
├── app.py              # Streamlit application
├── requirements.txt    # Python dependencies
└── README.md
```
