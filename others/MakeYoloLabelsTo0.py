import os

dataset_path = 'E:/anul 4/licenta/dataset/fruitsandvegetableslabel2'
annotations_path = os.path.join(dataset_path, 'test', 'labels')

new_class_id = 0

print(f"Start to process: {annotations_path}")

for label_file in os.listdir(annotations_path):
    label_path = os.path.join(annotations_path, label_file)

    if label_file.endswith('.txt'):
        with open(label_path, 'r') as file:
            lines = file.readlines()

        new_lines = []
        for line in lines:
            parts = line.split()
            original_class_id = parts[0]
            parts[0] = str(new_class_id)
            new_line = ' '.join(parts) + '\n'
            new_lines.append(new_line)

        with open(label_path, 'w') as file:
            file.writelines(new_lines)

        print(f"Processed file: {label_file}")

print("All labels were updated successfully.")
