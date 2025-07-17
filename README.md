# Vision-Assisted Nutrition Monitoring System

This project is a full-stack application designed to help users monitor their daily nutrition intake using computer vision. By combining object detection (YOLOv11) and image classification (ResNet-50), the system can automatically identify fruits and vegetables from user-submitted images and estimate their nutritional values.

## Key Features

- **Food detection** using a custom-trained YOLOv11 model  
- **Accurate classification** of fruits and vegetables with ResNet-50  
- **Automatic nutritional estimation** based on detected food items  
- **User profile management** with personalized dietary tracking  
- **Modular microservice architecture** built with Spring Boot, FastAPI, and React  
- **RESTful API integration** for seamless communication between services  
- **Dockerized deployment** for easy setup and scalability  

## Tech Stack

- **Backend:** Spring Boot (Java), FastAPI (Python)  
- **Frontend:** React, TypeScript  
- **Computer Vision:** YOLOv11, ResNet-50, PyTorch  
- **Database:** PostgreSQL  
- **Infrastructure:** Docker, Docker Compose  

## Use Case

This system is particularly useful for individuals looking to monitor their nutritional intake through images rather than manual input, offering a more intuitive and automated alternative to traditional food tracking apps.
