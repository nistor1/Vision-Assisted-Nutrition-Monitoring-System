import torch
import torchvision.transforms as transforms
from PIL import Image
import os
from ultralytics import YOLO

# Path to trained YOLO model
yolo_model_path = "E:/anul 4/LICENTA INVATARE/runs/detect/train2/weights/best.pt"
yolo_model = YOLO(yolo_model_path)
yolo_model.eval()

# Path to save cropped images
output_dir = "E:/anul 4/LICENTA_NISTOR_IOAN_GABRIEL/datasets/yolo_crops/"

# Transform for saved images
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
])

image_dir = "E:/anul 4/licenta/dataset/zucchini/"
images = [f for f in os.listdir(image_dir) if f.endswith('.jpg')]

for img_name in images:
    image_path = os.path.join(image_dir, img_name)
    image = Image.open(image_path).convert("RGB")
    width, height = image.size

    results = yolo_model(image_path)

    for result in results:
        for bbox, cls in zip(result.boxes.xywh, result.boxes.cls):
            x_c, y_c, w, h = bbox
            class_id = int(cls.item())

            x_min = int((x_c - w / 2))
            y_min = int((y_c - h / 2))
            x_max = int((x_c + w / 2))
            y_max = int((y_c + h / 2))

            cropped_img = image.crop((x_min, y_min, x_max, y_max))

            class_folder = os.path.join(output_dir, str(class_id))
            os.makedirs(class_folder, exist_ok=True)

            save_path = os.path.join(class_folder, f"{img_name}_crop.jpg")
            cropped_img.save(save_path)

print("Cropped images saved!")
