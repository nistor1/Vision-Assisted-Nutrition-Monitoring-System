import os

# Folders Paths
dataset_path = "E:/anul 4/licenta/dataset/vegetable_1.v1i.yolov11"
labels_path = os.path.join(dataset_path, "test", "labels")
images_path = os.path.join(dataset_path, "test", "images")

# Find empty label files
empty_files = [f for f in os.listdir(labels_path) if os.path.getsize(os.path.join(labels_path, f)) == 0]

# Delete empty label files and corresponding images
for empty_file in empty_files:
    label_path = os.path.join(labels_path, empty_file)
    image_name = empty_file.replace(".txt", ".jpg")
    image_path = os.path.join(images_path, image_name)

    if os.path.exists(image_path):
        os.remove(image_path)
        print(f"Image deleted: {image_path}")

    os.remove(label_path)
    print(f"No label file deleted: {label_path}")

print("All images and empty label files deleted.")
