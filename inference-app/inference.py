from PIL import Image, ImageOps
import torchvision.transforms.functional as tf
import torchvision.transforms as transforms
import torch


def resize_with_padding(image, target_size=224):
    w, h = image.size
    scale = target_size / max(w, h)
    new_w, new_h = int(w * scale), int(h * scale)
    image = tf.resize(image, (new_h, new_w))

    # Calculate padding
    pad_left = (target_size - new_w) // 2
    pad_top = (target_size - new_h) // 2
    pad_right = target_size - new_w - pad_left
    pad_bottom = target_size - new_h - pad_top

    # Add black padding
    image = tf.pad(image, (pad_left, pad_top, pad_right, pad_bottom), fill=0)
    return image

resnet_transform = transforms.Compose([
    transforms.Lambda(resize_with_padding),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406],
                         std=[0.229, 0.224, 0.225])
])

def detect_and_crop(image_path, model_yolo):
    image = Image.open(image_path).convert("RGB")
    results = model_yolo(image_path, imgsz=640)
    cropped_images = []

    for result in results:
        for bbox in result.boxes.xywh:
            x_c, y_c, w, h = bbox
            x_min = int((x_c - w / 2))
            y_min = int((y_c - h / 2))
            x_max = int((x_c + w / 2))
            y_max = int((y_c + h / 2))
            cropped_img = image.crop((x_min, y_min, x_max, y_max))
            cropped_images.append(cropped_img)

    return cropped_images if cropped_images else [image]

def classify_with_resnet(image, model_resnet, device, class_names):
    image = resnet_transform(image).unsqueeze(0).to(device)
    with torch.no_grad():
        output = model_resnet(image)
        predicted_class = torch.argmax(output, dim=1).item()
    return class_names[predicted_class]
