import os
from PIL import Image

def generate_block_positions_from_folder(input_folder="input"):
    try:
        image_files = [f for f in os.listdir(input_folder) if f.endswith(".png")]

        layers = []
        for filename in image_files:
            name, _ = os.path.splitext(filename)
            try:
                layer_num = int(name)
                layers.append(layer_num)
            except ValueError:
                print(f"Skipping {filename} (not a valid layer name)")
        
        layers.sort()

        for layer_num in layers:
            image_path = os.path.join(input_folder, f"{layer_num}.png")
            print(f"\n// Layer {layer_num}")

            img = Image.open(image_path)
            width, height = img.size

            if width != height or width % 2 == 0:
                raise ValueError(f"{image_path}: Image must be square and have an odd number of pixels per side.")

            img = img.convert("RGB")
            center = width // 2

            positions = []
            for y in range(height):
                for x in range(width):
                    r, g, b = img.getpixel((x, y))
                    if (r + g + b) != 255 * 3:  # not white
                        rel_x = x - center
                        rel_z = y - center
                        positions.append((rel_x, rel_z))

            for (rel_x, rel_z) in positions:
                print(f"new BlockPos({rel_x}, {layer_num}, {rel_z}),")

    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    generate_block_positions_from_folder()
