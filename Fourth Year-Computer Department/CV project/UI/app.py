import streamlit as st
import cv2
import numpy as np
import pandas as pd
from sklearn.metrics import confusion_matrix, accuracy_score, precision_score, recall_score, f1_score
import plotly.express as px
from tensorflow.keras.models import load_model
from tensorflow.keras.applications.mobilenet_v2 import preprocess_input as mobile_pre
from tensorflow.keras.applications.efficientnet import preprocess_input as eff_pre

st.set_page_config(page_title="Driver Drowsiness System", layout="wide")

@st.cache_resource
def load_all_models():
    try:
        custom = load_model("models/custom_cnn_best.keras")
        mobile = load_model("models/mobileNet_best_model.keras")
        eff = load_model("models/efficientnet_best_model.keras") 
        return custom, mobile, eff
    except Exception as e:
        st.error(f"Error loading models: {e}")
        return None, None, None

face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")
model_custom, model_mobile, model_eff = load_all_models()

def crop_face(frame):
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.3, minNeighbors=5)
    if len(faces) == 0:
        return frame
    x, y, w, h = max(faces, key=lambda b: b[2]*b[3])
    return frame[y:y+h, x:x+w]

def process_frame(frame, model_choice):
    frame = crop_face(frame)
    if model_choice == "Custom CNN":
        img = cv2.resize(frame, (64, 64))
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img = img.astype("float32") / 255.0
        return img.reshape(1, 64, 64, 3)
    elif model_choice == "MobileNetV2":
        img = cv2.resize(frame, (224, 224))
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img = img.astype("float32")
        img = mobile_pre(img)
        return img.reshape(1, 224, 224, 3)
    else:
        img = cv2.resize(frame, (224, 224))
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img = img.astype("float32")
        img = eff_pre(img)
        return img.reshape(1, 224, 224, 3)

st.title("Driver Drowsiness Monitoring Dashboard")

model_choice = st.selectbox("Choose Model", ["Custom CNN", "MobileNetV2", "EfficientNetB0"])

st.header("Models Benchmarking")
df = pd.DataFrame({
    "Model Name": ["Custom CNN", "MobileNetV2", "EfficientNetB0"],
    "Test Accuracy": [0.9992, 0.9994, 0.9994],
    "Test Precision": [0.9993, 0.9994, 0.9993],
    "Test Recall": [0.9993, 0.9994, 0.9993],
    "Test Loss": [0.000512, 0.0005, 5.12e-04]
})
col1, col2 = st.columns(2)
with col1:
    st.dataframe(df, use_container_width=True)
with col2:
    fig = px.bar(df, x="Model Name", y="Test Accuracy", text_auto=True, color="Model Name")
    st.plotly_chart(fig, use_container_width=True)

st.divider()
st.header("Case Studies Visualization")

cases = [
    {"title": "Alert State", "img": "assets/alert.png", "label": "SAFE"},
    {"title": "Alert State (Test)", "img": "assets/alert_test.jpg", "label": "SAFE"},
    {"title": "Alert State (Test)", "img": "assets/alert2.jpg", "label": "SAFE"},
    {"title": "Alert State (Test)", "img": "assets/alert3.jpg", "label": "SAFE"},
    {"title": "Alert State (Test)", "img": "assets/alert4.jpg", "label": "SAFE"},
    {"title": "Drowsy State", "img": "assets/sleep.png", "label": "DROWSY"},
    {"title": "Drowsy State (Test)", "img": "assets/sleep_test.jpg", "label": "DROWSY"},
    {"title": "Drowsy State (Test)", "img": "assets/sleep2.jpg", "label": "DROWSY"},
    {"title": "Drowsy State (Test)", "img": "assets/sleep3.jpg", "label": "DROWSY"},
    {"title": "Drowsy State (Test)", "img": "assets/sleep4.jpg", "label": "DROWSY"},
]

all_models = {
    "Custom CNN": model_custom,
    "MobileNetV2": model_mobile,
    "EfficientNetB0": model_eff
}

all_predictions = {m: [] for m in all_models.keys()}

cols_per_row = 3
y_true_display, y_pred_display = [], []

st.subheader(f"Predictions using {model_choice}")
for i in range(0, len(cases), cols_per_row):
    row_cases = cases[i:i+cols_per_row]
    cols = st.columns(len(row_cases))
    for idx, case in enumerate(row_cases):
        img = cv2.imread(case["img"])
        if img is None:
            st.error(f"Image {case['img']} not found")
            continue
        y_true_display.append(case["label"])
        pred_labels_for_row = {}

        for m_name, model in all_models.items():
            p = process_frame(img, m_name)
            if m_name == "Custom CNN":
                prob = model.predict(p, verbose=0)[0][0]
                pred_label = "SAFE" if prob >= 0.5 else "DROWSY"
            elif m_name == "MobileNetV2":
                prob = model.predict(p, verbose=0)[0][0]
                pred_label = "SAFE" if prob >= 0.35 else "DROWSY"
            else:
                prob = model.predict(p, verbose=0)[0][0]
                pred_label = "SAFE" if prob >= 0.5 else "DROWSY"
            pred_labels_for_row[m_name] = pred_label
            all_predictions[m_name].append(pred_label)

        y_pred_display.append(pred_labels_for_row[model_choice])
        cols[idx].image(cv2.cvtColor(cv2.resize(img, (300, 300)), cv2.COLOR_BGR2RGB), width=300)
        cols[idx].markdown(f"**Actual:** {case['label']}  \n**Predicted:** {pred_labels_for_row[model_choice]}")


accuracy = accuracy_score(y_true_display, y_pred_display)
precision = precision_score(y_true_display, y_pred_display, pos_label="SAFE")
recall = recall_score(y_true_display, y_pred_display, pos_label="SAFE")
f1 = f1_score(y_true_display, y_pred_display, pos_label="SAFE")

st.divider()
st.header("Summary Metrics")
summary_df = pd.DataFrame({
    "Metric": ["Accuracy", "Precision", "Recall", "F1-score"],
    "Score": [accuracy, precision, recall, f1]
})
st.dataframe(summary_df, use_container_width=True)

st.subheader("Confusion Matrix")
cm = confusion_matrix(y_true_display, y_pred_display, labels=["SAFE","DROWSY"])
st.dataframe(pd.DataFrame(cm, index=["Actual SAFE","Actual DROWSY"], columns=["Predicted SAFE","Predicted DROWSY"]), use_container_width=True)


results_summary = {}
for m_name, preds in all_predictions.items():
    acc_model = accuracy_score(y_true_display, preds)
    rec_model = recall_score(y_true_display, preds, pos_label="SAFE")
    results_summary[m_name] = {"accuracy": acc_model, "recall": rec_model}

best_model = max(results_summary.items(), key=lambda x: (x[1]["accuracy"], x[1]["recall"]))

st.divider()
st.header("Best Model Summary")
st.markdown(f"**Best Model:** {best_model[0]}")
st.markdown(f"**Accuracy:** {best_model[1]['accuracy']:.3f}")
st.markdown(f"**Recall:** {best_model[1]['recall']:.3f}")
