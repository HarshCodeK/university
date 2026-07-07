"""
Dog vs Cat Image Classifier
Uses pre-trained MobileNetV2 to classify uploaded images as Dog or Cat.
"""

import streamlit as st
import numpy as np
from PIL import Image
import tensorflow as tf
from tensorflow.keras.applications.mobilenet_v2 import MobileNetV2, preprocess_input, decode_predictions

# ImageNet class ranges: cats = 281-285, dogs = 151-268
CAT_CLASSES = set(range(281, 286))
DOG_CLASSES = set(range(151, 269))

@st.cache_resource
def load_model():
    """Load pre-trained MobileNetV2 (cached for performance)."""
    return MobileNetV2(weights="imagenet")

def predict_dog_cat(image, model):
    """Predict whether image contains a dog or cat."""
    img = image.resize((224, 224))
    img_array = np.array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array = preprocess_input(img_array)

    preds = model.predict(img_array, verbose=0)
    decoded = decode_predictions(preds, top=5)[0]

    dog_conf = 0.0
    cat_conf = 0.0

    for _, class_name, prob in decoded:
        class_id = _get_imagenet_class_id(class_name)
        if class_id in DOG_CLASSES:
            dog_conf += prob
        elif class_id in CAT_CLASSES:
            cat_conf += prob

    if dog_conf > cat_conf:
        return "Dog", dog_conf * 100
    else:
        return "Cat", cat_conf * 100

# Cache for class ID mapping (simplified using known WordNet IDs)
@st.cache_data
def _get_imagenet_class_id(class_name):
    """Map class name to ImageNet ID range using keyword matching."""
    name_lower = class_name.lower()
    # Known dog-related keywords
    dog_keywords = ["dog", "puppy", "hound", "terrier", "retriever", "shepherd", "poodle",
                    "mastiff", "beagle", "dalmatian", "chihuahua", "husky", "malamute",
                    "collie", "spaniel", "setter", "pointer", "greyhound", "whippet",
                    "pug", "bulldog", "boxer", "doberman", "rottweiler", "corgi",
                    "shih", "bichon", "maltese", "pomeranian", "yorkshire", "cocker"]
    # Known cat-related keywords
    cat_keywords = ["cat", "kitten", "tabby", "tiger cat", "persian cat", "siamese cat",
                    "egyptian cat", "cougar", "lynx", "leopard", "jaguar", "cheetah"]

    for kw in dog_keywords:
        if kw in name_lower:
            return 200  # Any dog class ID in range
    for kw in cat_keywords:
        if kw in name_lower:
            return 281  # Any cat class ID in range
    return -1

# --- Streamlit UI ---
st.set_page_config(page_title="Dog vs Cat Classifier", layout="centered")

st.title("Dog vs Cat Image Classifier")
st.markdown("Upload an image and the model will predict whether it's a **Dog** or **Cat**.")

uploaded_file = st.file_uploader("Choose an image...", type=["jpg", "jpeg", "png"])

if uploaded_file is not None:
    image = Image.open(uploaded_file)
    st.image(image, caption="Uploaded Image", use_column_width=True)

    with st.spinner("Loading model and analyzing..."):
        model = load_model()
        label, confidence = predict_dog_cat(image, model)

    col1, col2 = st.columns(2)
    with col1:
        st.metric("Prediction", label)
    with col2:
        st.metric("Confidence", f"{confidence:.1f}%")

    if label == "Dog":
        st.success("This looks like a dog! ")
    else:
        st.success("This looks like a cat! ")

    st.caption("Note: This uses MobileNetV2 pre-trained on ImageNet. Accuracy depends on image quality.")
