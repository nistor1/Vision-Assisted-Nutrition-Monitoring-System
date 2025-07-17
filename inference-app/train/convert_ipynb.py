import subprocess
import os

# Numele fișierului notebook
notebook_file = "train-resnet.ipynb"  # înlocuiește cu numele tău
output_dir = "."  # folderul în care vrei să salvezi fișierul .py ('.' = director curent)

# Verificăm dacă fișierul există
if not os.path.isfile(notebook_file):
    print(f"Fișierul {notebook_file} nu există în directorul curent: {os.getcwd()}")
else:
    print(f"Conversie {notebook_file} → .py ...")
    subprocess.run([
        "jupyter", "nbconvert",
        "--to", "script",
        "--output-dir", output_dir,
        notebook_file
    ])
    print("✅ Conversie finalizată!")
