import cv2
import numpy as np
import tensorflow as tf
from tensorflow import keras

def load_model(model_path):
    """
    Load the pre-trained model from the specified path.
    """
    model = keras.models.load_model(model_path)
    return model

def enhance_image(model, image_path, output_path):
    """
    Enhance the input image using the loaded model and save the result.
    """
    # Read the input image
    image = cv2.imread(image_path)
    # Convert the image from BGR to RGB
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    # Resize the image to the model's input size
    image = cv2.resize(image, (model.input_shape[1], model.input_shape[2]))
    # Expand dimensions to fit model input shape
    image = np.expand_dims(image, axis=0)
    # Predict the enhanced image
    enhanced_image = model.predict(image)
    # Squeeze the dimensions to remove the extra batch dimension
    enhanced_image = np.squeeze(enhanced_image, axis=0)
    # Convert the image back to BGR
    enhanced_image = cv2.cvtColor(enhanced_image, cv2.COLOR_RGB2BGR)
    # Save the enhanced image
    cv2.imwrite(output_path, enhanced_image)

if __name__ == "__main__":
    import sys
    # Ensure the correct arguments are passed
    if len(sys.argv) != 3:
        print("Usage: python enhance_image.py <input_image_path> <output_image_path>")
        sys.exit(1)
    # Load the pre-trained model
    model_path = 'path_to_your_pretrained_model.h5'
    model = load_model(model_path)
    # Enhance the image using the provided input and output paths
    enhance_image(model, sys.argv[1], sys.argv[2])
