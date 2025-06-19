from model_loader import load_models
from inference import detect_and_crop, classify_with_resnet
from collections import Counter
from fastapi import FastAPI, UploadFile, File
import shutil
import os

app = FastAPI()

# Load models only once
yolo_model, resnet_model, device = load_models()
class_names = [str(i) for i in range(30)]

@app.post("/predict/")
async def predict(file: UploadFile = File(...)):
    temp_file = f"temp_{file.filename}"
    with open(temp_file, "wb") as buffer:
        shutil.copyfileobj(file.file, buffer)

    # Inference
    cropped_images = detect_and_crop(temp_file, yolo_model)
    labels = []
    for cropped in cropped_images:
        label = classify_with_resnet(cropped, resnet_model, device, class_names)
        labels.append(label)

    os.remove(temp_file)

    counts = Counter(labels)

    results = [{"tag": int(tag), "count": count} for tag, count in counts.items()]

    return results
