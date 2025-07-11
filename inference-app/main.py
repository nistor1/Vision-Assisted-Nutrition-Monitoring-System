import os
import shutil
import base64
from collections import Counter
from fastapi import FastAPI, UploadFile, File, HTTPException, Depends
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from jose import jwt, JWTError

from model_loader import load_models
from inference import detect_and_crop, classify_with_resnet

# === JWT CONFIG ===
BASE64_SECRET = "dW4tc2VjcmV0LWFsZWF0b3ItY29tcGxleC1kZS1taW5pbS0yNTYtYml0cw=="
ALGORITHM = "HS256"
SECRET_KEY = base64.b64decode(BASE64_SECRET)

# === SECURITY SCHEME ===
security = HTTPBearer()

def verify_jwt(credentials: HTTPAuthorizationCredentials = Depends(security)):
    try:
        return jwt.decode(credentials.credentials, SECRET_KEY, algorithms=[ALGORITHM])
    except JWTError:
        raise HTTPException(status_code=401, detail="Invalid or expired JWT token")

# === INIT FASTAPI ===
app = FastAPI()

# === LOAD MODELS ===
yolo_model, resnet_model, device = load_models()
class_names = [str(i) for i in range(30)]

# === PREDICT ENDPOINT ===
@app.post("/predict/")
async def predict(
    file: UploadFile = File(...),
    payload: dict = Depends(verify_jwt)
):
    temp_file = f"temp_{file.filename}"
    try:
        with open(temp_file, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        cropped_images = detect_and_crop(temp_file, yolo_model)

        labels = [
            classify_with_resnet(img, resnet_model, device, class_names)
            for img in cropped_images
        ]
        counts = Counter(labels)

        return [{"tag": int(tag), "count": count} for tag, count in counts.items()]

    except Exception:
        raise HTTPException(status_code=500, detail="Error during inference")

    finally:
        if os.path.exists(temp_file):
            os.remove(temp_file)
