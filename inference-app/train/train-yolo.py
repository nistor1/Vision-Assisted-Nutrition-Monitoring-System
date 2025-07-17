import os
import subprocess
import sys

# 1. Afișează info GPU
subprocess.run(["nvidia-smi"])

# 2. Setează HOME
HOME = os.getcwd()
print("HOME:", HOME)

# 3. Instalează pachetele necesare
subprocess.run([sys.executable, "-m", "pip", "install", "ultralytics", "supervision", "roboflow"])
subprocess.run([sys.executable, "-m", "pip", "install", 'ray[tune]'])
subprocess.run([sys.executable, "-m", "pip", "install", "ray==2.7.1"])

# 4. Verificare ultralytics
import ultralytics
ultralytics.checks()

# 5. Creează directorul datasets
datasets_path = os.path.join(HOME, "datasets")
os.makedirs(datasets_path, exist_ok=True)
os.chdir(datasets_path)

# 6. Afișează fișierele din dataset
dataset = "/kaggle/input/fruitsandvegetablesroi/"
print("Fișiere disponibile:", os.listdir(dataset))

# 7. Revin în HOME
os.chdir(HOME)

# 8. Comandă YOLO - prima rundă de antrenare
command1 = """
yolo task=detect mode=train model=/kaggle/input/run_roi_2/pytorch/default/1/last.pt \
data=/kaggle/input/fruitsandvegetablesroi/data.yaml epochs=35 \
imgsz=640 batch=22 workers=8 device=0,1 optimizer=AdamW \
lr0=0.002 lrf=0.01 momentum=0.937 weight_decay=0.0004 \
warmup_epochs=3 warmup_momentum=0.8 warmup_bias_lr=0.1 \
cache=False dropout=0.05 \
hsv_h=0.015 hsv_s=0.4 hsv_v=0.3 \
degrees=2 translate=0.05 scale=0.2 shear=1.0 \
flipud=0.05 fliplr=0.2 mosaic=0.3 mixup=0.05 copy_paste=0.05 \
close_mosaic=2 overlap_mask=True mask_ratio=4 \
patience=10 save=True plots=True val=True
"""
subprocess.run(command1, shell=True)

# 9. Arhivare rezultate
subprocess.run(["zip", "-r", "/kaggle/working/runs.zip", "/kaggle/working/runs"])

# 10. Comandă YOLO - a doua rundă de antrenare
command2 = """
yolo task=detect mode=train model=/kaggle/input/runs_roi_3_last/pytorch/default/1/last.pt \
data=/kaggle/input/fruitsandvegetablesroi/data.yaml epochs=20 \
imgsz=640 batch=22 workers=8 device=0,1 optimizer=AdamW \
lr0=0.0035 lrf=0.015 momentum=0.94 weight_decay=0.0004 \
warmup_epochs=3 warmup_momentum=0.85 warmup_bias_lr=0.1 \
cache=True dropout=0.05 \
hsv_h=0.015 hsv_s=0.5 hsv_v=0.3 \
degrees=5 translate=0.1 scale=0.3 shear=2.0 \
flipud=0.1 fliplr=0.4 mosaic=0.8 mixup=0.1 copy_paste=0.1 \
close_mosaic=2 overlap_mask=True mask_ratio=4 \
save=True plots=True val=True
"""
subprocess.run(command2, shell=True)

# 11. Re-arhivare rezultate finale
subprocess.run(["zip", "-r", "/kaggle/working/runs.zip", "/kaggle/working/runs"])
