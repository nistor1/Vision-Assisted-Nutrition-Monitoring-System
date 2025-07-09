import torch
from torchvision import models
from ultralytics import YOLO

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

def load_models():
    # YOLO model
    yolo_model_path = "yolo_model.pt"
    yolo_model = YOLO(yolo_model_path)
    yolo_model.to(device)
    yolo_model.eval()

    # ResNet model
    num_classes = 30
    resnet = models.resnet50(weights=models.ResNet50_Weights.IMAGENET1K_V2)
    num_ftrs = resnet.fc.in_features
    resnet.fc = torch.nn.Linear(num_ftrs, num_classes)
    resnet.load_state_dict(torch.load(
        "resnet_model.pth",
        map_location=device
    ))
    resnet = resnet.to(device)
    resnet.eval()

    return yolo_model, resnet, device
