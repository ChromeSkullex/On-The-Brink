from PIL import Image
import sys

# Function to generate block positions from an image
def generate_block_positions(image_path, y_coordinate):
    try:
        # Open the image
        img = Image.open(image_path)
        
        # Ensure the image is square and has odd dimensions
        width, height = img.size
        if width != height or width % 2 == 0:
            raise ValueError("Image must be square and have an odd number of pixels per side.")

        # Convert the image to RGB (in case it's not already)
        img = img.convert("RGB")

        # Center of the image
        center = width // 2

        # Initialize the list for storing coordinates
        positions = []

        # Loop through the pixels of the image
        for y in range(height):
            for x in range(width):
                r, g, b = img.getpixel((x, y))
                # Check if the pixel is green (#00FF00)
                if r == 0 and g == 255 and b == 0:
                    # Calculate relative coordinates
                    rel_x = x - center
                    rel_z = y - center
                    positions.append((rel_x, rel_z))

        # Loop through the filled list and print the BlockPos lines
        for pixel in positions:
            print(f"new BlockPos({pixel[0]}, {y_coordinate}, {pixel[1]}),")

    except Exception as e:
        print(f"Error: {e}")

# Example usage
if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python script.py <image_path> <y_coordinate>")
    else:
        image_path = sys.argv[1]
        y_coordinate = sys.argv[2]
        generate_block_positions(image_path, y_coordinate)
