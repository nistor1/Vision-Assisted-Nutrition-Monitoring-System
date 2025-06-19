from PIL import Image, ImageOps
import torchvision.transforms as transforms
import torch

def pad_to_square(image):
    width, height = image.size
    max_dim = max(width, height)
    delta_w = max_dim - width
    delta_h = max_dim - height
    padding = (delta_w // 2, delta_h // 2, delta_w - delta_w // 2, delta_h - delta_h // 2)
    return ImageOps.expand(image, padding, fill=(0, 0, 0))

resnet_transform = transforms.Compose([
    transforms.Lambda(pad_to_square),
    transforms.Resize(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406],
                         std=[0.229, 0.224, 0.225])
])

def detect_and_crop(image_path, model_yolo):
    image = Image.open(image_path).convert("RGB")
    results = model_yolo(image_path)
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
