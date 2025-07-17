#!/usr/bin/env python
# coding: utf-8

# In[3]:


import torch
import torch.nn as nn
import torchvision.models as models
from torchvision import datasets, transforms
from torch.utils.data import DataLoader, random_split
import os
from torch.optim.lr_scheduler import StepLR


# In[5]:


#Check if there are available GPUs
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
multi_gpu = torch.cuda.device_count() > 1
print(f"🚀 Device: {device} | GPUs Available: {torch.cuda.device_count()}")


# In[6]:


data_dir = "/kaggle/input/yolo-crops-v3/yolo_crops"


# In[8]:


import torchvision.transforms.functional as TF

def resize_with_padding(image, target_size=224):
    w, h = image.size
    scale = target_size / max(w, h)
    new_w, new_h = int(w * scale), int(h * scale)
    image = TF.resize(image, (new_h, new_w))
    
    #Calculate padding
    pad_left = (target_size - new_w) // 2
    pad_top = (target_size - new_h) // 2
    pad_right = target_size - new_w - pad_left
    pad_bottom = target_size - new_h - pad_top
    
    #Add black padding (0)
    image = TF.pad(image, (pad_left, pad_top, pad_right, pad_bottom), fill=0)
    return image


data_transforms = {
    "train": transforms.Compose([
        transforms.Lambda(lambda img: resize_with_padding(img, 224)),
        transforms.RandomHorizontalFlip(),
        transforms.ColorJitter(brightness=0.2, contrast=0.2, saturation=0.2, hue=0.1),
        transforms.RandomRotation(15),
        transforms.RandomAffine(degrees=0, translate=(0.1, 0.1)),
        transforms.RandomPerspective(distortion_scale=0.2, p=0.5),
        transforms.ToTensor(),
        transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
    ]),
    "val": transforms.Compose([
        transforms.Lambda(lambda img: resize_with_padding(img, 224)),
        transforms.ToTensor(),
        transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
    ])
}


# In[9]:


#Load labels and images
dataset = datasets.ImageFolder(root=data_dir, transform=data_transforms["train"])
train_size = int(0.8 * len(dataset))
val_size = len(dataset) - train_size
train_dataset, val_dataset = random_split(dataset, [train_size, val_size])

#Apply tranforms
train_dataset.dataset.transform = data_transforms["train"]
val_dataset.dataset.transform = data_transforms["val"]


# In[14]:


#DataLoader
batch_size = 128
num_workers = 4 if device == "cuda" else 2
train_loader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True, num_workers=num_workers, pin_memory=True)
val_loader = DataLoader(val_dataset, batch_size=batch_size, shuffle=False, num_workers=num_workers, pin_memory=True)


# In[ ]:


import torch
import torch.nn as nn
import torch.optim as optim
from torch.optim.lr_scheduler import StepLR
from torchvision import datasets, models, transforms
import torch.amp  # Pentru mixed precision

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

#Init with pretrained ResNet
model = models.resnet50(weights=models.ResNet50_Weights.IMAGENET1K_V2)
num_ftrs = model.fc.in_features
model.fc = nn.Linear(num_ftrs, len(dataset.classes))  # Asigură-te că 'dataset.classes' este corect definit

model = model.to(device)

#Definie Loss Function and Optimizer
criterion = nn.CrossEntropyLoss()
optimizer = torch.optim.Adam(model.parameters(), lr=0.001)
scheduler = StepLR(optimizer, step_size=3, gamma=0.1)  # Reduce lr at every 3 epochs

# Mixed Precision Training
scaler = torch.amp.GradScaler()

num_epochs = 10

#Training
for epoch in range(num_epochs):
    print("=" * 50)
    print(f"START Epoch [{epoch+1}/{num_epochs}]")
    print("=" * 50)

    model.train()  #Model in train mode
    running_loss = 0.0

    for inputs, labels in train_loader:
        inputs, labels = inputs.to(device, non_blocking=True), labels.to(device, non_blocking=True)

        optimizer.zero_grad()  # Reset gradients

        #Mixed Precision
        with torch.amp.autocast(device_type='cuda'):
            outputs = model(inputs)
            loss = criterion(outputs, labels)

        #Backpropagation with mixed precision
        scaler.scale(loss).backward()  #Calculate gradients
        scaler.step(optimizer)  #Optimizer step
        scaler.update()

        running_loss += loss.item()  #Add loss for this batch

    scheduler.step()  #Update scheduler for lr

    #Eval on validation set
    model.eval()  #Model in evaluation set
    val_loss, correct, total = 0.0, 0, 0

    with torch.no_grad():
        for inputs, labels in val_loader:
            inputs, labels = inputs.to(device, non_blocking=True), labels.to(device, non_blocking=True)

            with torch.amp.autocast(device_type='cuda'):
                outputs = model(inputs)
                loss = criterion(outputs, labels)

            val_loss += loss.item()  #Add loss for this batchh
            _, preds = torch.max(outputs, 1)  #Predictions
            correct += (preds == labels).sum().item()
            total += labels.size(0)

    val_acc = correct / total  #Calculate Accuracy
    print("-" * 50)
    print(f"Epoch [{epoch+1}/{num_epochs}] COMPLETED")
    print(f"Train Loss: {running_loss/len(train_loader):.4f}")
    print(f"Val Loss: {val_loss/len(val_loader):.4f} | Accuracy: {val_acc:.4%}")
    print("=" * 50)

    #Save model each epoch
    model_save_path = f"resnet_fruits_epoch_{epoch+1}.pth"
    torch.save(model.state_dict(), model_save_path)
    print(f"Model Saved: {model_save_path}")

#Save final model
model_save_path_final = "resnet_fruits_final.pth"
torch.save(model.state_dict(), model_save_path_final)
print(f"Final Model Saved: {model_save_path_final}")


# In[57]:


import torch
import torch.nn as nn
import torchvision.models as models
from torch.optim.lr_scheduler import ReduceLROnPlateau

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

#Load the pre-trained ResNet50 model and replace the classification head
model = models.resnet50(weights=models.ResNet50_Weights.IMAGENET1K_V2)
num_ftrs = model.fc.in_features
model.fc = nn.Linear(num_ftrs, 30)

#Load the weights from the previously trained model
model.load_state_dict(torch.load("/kaggle/input/resnet_fruits_best_6/pytorch/default/1/resnet_fruits_epoch_6.pth", map_location=device))
model = model.to(device)
print("Model loaded!")

#Freeze all layers except for the final fully-connected (fc) layer
for name, param in model.named_parameters():
    param.requires_grad = name.startswith("fc")

#Define optimizer for the final layer only with low learning rate
optimizer = torch.optim.Adam(model.fc.parameters(), lr=1e-4, weight_decay=1e-4)
criterion = nn.CrossEntropyLoss()

#Learning rate scheduler based on validation accuracy
scheduler = ReduceLROnPlateau(optimizer, mode='max', factor=0.3, patience=1, threshold=0.002)
scaler = torch.amp.GradScaler()

num_epochs = 3

#Fine-tuning loop (only for the final layer)
for epoch in range(num_epochs):
    print(f"\nEpoch {epoch+1}/{num_epochs}")
    model.train()
    train_loss = 0.0

    for inputs, labels in train_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        optimizer.zero_grad()

        with torch.amp.autocast(device_type='cuda'):
            outputs = model(inputs)
            loss = criterion(outputs, labels)

        scaler.scale(loss).backward()
        scaler.step(optimizer)
        scaler.update()
        train_loss += loss.item()

    #Evaluation on validation set
    model.eval()
    val_loss, correct, total = 0.0, 0, 0
    with torch.no_grad():
        for inputs, labels in val_loader:
            inputs, labels = inputs.to(device), labels.to(device)
            with torch.amp.autocast(device_type='cuda'):
                outputs = model(inputs)
                loss = criterion(outputs, labels)

            val_loss += loss.item()
            _, preds = torch.max(outputs, 1)
            correct += (preds == labels).sum().item()
            total += labels.size(0)

    val_acc = correct / total
    scheduler.step(val_acc)

    print(f"Train Loss: {train_loss/len(train_loader):.4f}")
    print(f"Val Loss: {val_loss/len(val_loader):.4f} | Accuracy: {val_acc:.4%}")

    #Save model after each epoch
    torch.save(model.state_dict(), f"resnet_fruits_finetuned_fc_only_epoch_{epoch+1}.pth")

#Save the final fine-tuned model
torch.save(model.state_dict(), "resnet_fruits_finetuned_fc_only_best.pt")
print("Fine-tuning completed and model saved!")


# In[ ]:


model.eval()
correct = 0
total = 0

with torch.no_grad():
    for inputs, labels in val_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = model(inputs)
        _, predicted = torch.max(outputs, 1)
        total += labels.size(0)
        correct += (predicted == labels).sum().item()

accuracy = 100 * correct / total
print(f"Accuracy on validation set: {accuracy:.2f}%")


# # Validation for Trained model

# In[16]:


from sklearn.metrics import confusion_matrix, classification_report, accuracy_score
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np


# In[ ]:


state_dict = torch.load("/kaggle/input/resnet_fruits_best_6/pytorch/default/1/resnet_fruits_epoch_6.pth", map_location=device)
model.load_state_dict(state_dict)
model = model.to(device)
model.eval()
print("Model loaded successfully from state_dict!")


# In[ ]:


y_true, y_pred = [], []

with torch.no_grad():
    for inputs, labels in val_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = model(inputs)
        _, preds = torch.max(outputs, 1)

        y_true.extend(labels.cpu().numpy())
        y_pred.extend(preds.cpu().numpy())


# In[ ]:


class_names = val_dataset.dataset.classes  # obține numele claselor

cm = confusion_matrix(y_true, y_pred)
plt.figure(figsize=(12, 10))
sns.heatmap(cm, annot=True, fmt="d", xticklabels=class_names, yticklabels=class_names, cmap="Blues")
plt.title("Matricea de Confuzie")
plt.xlabel("Etichete prezise")
plt.ylabel("Etichete reale")
plt.tight_layout()
plt.show()


# In[ ]:


report = classification_report(y_true, y_pred, target_names=class_names, output_dict=True)
df_report = pd.DataFrame(report).transpose()

print(df_report)


# In[ ]:


acc = accuracy_score(y_true, y_pred)
print(f"Acuratețe globală: {acc:.4%}")


# In[ ]:


f1_scores = df_report.iloc[:-3]["f1-score"]

plt.figure(figsize=(12, 6))
sns.barplot(x=f1_scores.index, y=f1_scores.values, color='skyblue')
plt.xticks(rotation=90)
plt.ylabel("F1-score")
plt.title("F1-score per clasă")
plt.tight_layout()
plt.show()


# # Validation for Fine-Tuned model

# In[ ]:


state_dict = torch.load("/kaggle/input/model_finetuned/pytorch/default/1/resnet_fruits_finetuned_fc_only_epoch_4.pth", map_location=device)
model.load_state_dict(state_dict)
model = model.to(device)
model.eval()
print("Model loaded successfully from state_dict!")


# In[28]:


y_true, y_pred = [], []

with torch.no_grad():
    for inputs, labels in val_loader:
        inputs, labels = inputs.to(device), labels.to(device)
        outputs = model(inputs)
        _, preds = torch.max(outputs, 1)

        y_true.extend(labels.cpu().numpy())
        y_pred.extend(preds.cpu().numpy())


# In[ ]:


class_names = val_dataset.dataset.classes  # obține numele claselor

cm = confusion_matrix(y_true, y_pred)
plt.figure(figsize=(12, 10))
sns.heatmap(cm, annot=True, fmt="d", xticklabels=class_names, yticklabels=class_names, cmap="Blues")
plt.title("Matricea de Confuzie")
plt.xlabel("Etichete prezise")
plt.ylabel("Etichete reale")
plt.tight_layout()
plt.show()


# In[ ]:


report = classification_report(y_true, y_pred, target_names=class_names, output_dict=True)
df_report = pd.DataFrame(report).transpose()

print(df_report)


# In[ ]:


acc = accuracy_score(y_true, y_pred)
print(f"Acuratețe globală: {acc:.4%}")


# In[ ]:


f1_scores = df_report.iloc[:-3]["f1-score"]

plt.figure(figsize=(12, 6))
sns.barplot(x=f1_scores.index, y=f1_scores.values, color='skyblue')
plt.xticks(rotation=90)
plt.ylabel("F1-score")
plt.title("F1-score per clasă")
plt.tight_layout()
plt.show()

