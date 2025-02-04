package com.example.imageUpscaler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageUpscaleApplication {

	static {
		// System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadLocally();
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageUpscaleApplication.class, args);
		System.out.println();
		defineDirectory();
		System.out.println("done");

	}

	public static void defineDirectory() {
		// String inputDirPath = "C:\\Users\\aatif\\OneDrive\\business\\design\\HD\\A";
		// String outputDirPath = "C:\\Users\\aatif\\OneDrive\\business\\design\\HD\\A
		// upscaled";

		String inputDirPath = "C:\\Users\\aatif\\Downloads\\delete";
		String outputDirPath = "C:\\Users\\aatif\\Downloads\\delete";

		upscaleImagesInDirectory(inputDirPath, outputDirPath);
	}

	private static void upscaleImagesInDirectory(String inputDirPath, String outputDirPath) {
		File inputDirectory = new File(inputDirPath);
		File outputDirectory = new File(outputDirPath);

		// Create output directory if it doesn't exist
		if (!outputDirectory.exists()) {
			outputDirectory.mkdir();
		}

		if (inputDirectory.isDirectory()) {
			File[] files = inputDirectory.listFiles(
					(dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

			if (files != null) {
				for (File file : files) {
					BufferedImage bimg = null;
					try {
						// bimg = ImageIO.read(new File(file.getName()));
						bimg = ImageIO.read(new File(file.toURI()));

					} catch (IOException e) {
						System.out.println("unable to read file dimension");
						e.printStackTrace();
					}
					int width = bimg.getWidth();
					int height = bimg.getHeight();
					double fileSize = file.length() / (1024 * 1024);
					upscaleImage(file.getName(), width, height, file.getPath(), outputDirPath);
				}
			} else {
				System.out.println("No image files found in the directory.");
			}
		} else {
			System.out.println("Provided path is not a directory.");
		}
	}

	private static void upscaleImage(String imageFileName, int width, int height, String filePath, String outputDirPath) {
	    Mat source = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_UNCHANGED);  // Load image with alpha channel if present
	    Mat destination = new Mat();

	    double targetWidth = 7000.0;
	    
		if (width != targetWidth) {
			double factor = targetWidth / width;

			// Check if image has an alpha channel (4 channels)
			boolean hasAlpha = source.channels() == 4;
			// Upscale by a factor
			System.out.println(imageFileName + " image dimension w x h: " + width + " x " + "height");
			System.out.println("upscaling image by factor: " + factor);
			if (hasAlpha) {
				try {
					Imgproc.resize(source, destination, new Size(source.cols() * factor, source.rows() * factor), 0, 0,
							Imgproc.INTER_CUBIC);
				} catch (Exception e) {
					System.out.println("ERROR in if block");
					e.printStackTrace();
				}
			} else {
				try {
					Imgproc.resize(source, destination, new Size(source.cols() * factor, source.rows() * factor), 0, 0,
							Imgproc.INTER_CUBIC);
				} catch (Exception e) {
					System.out.println("ERROR in else block");
					e.printStackTrace();
				}
			}


			// Increase DPI
			int dpi = 600;
			System.out.println("setting image DPI to: " + dpi);
			//MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_PNG_COMPRESSION, 9);
			MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_JPEG_CHROMA_QUALITY, 9);
			destination.put(0, 0, new byte[dpi]);

			// Create output file path
			String fileName = new File(filePath).getName();
			String outputFilePath = outputDirPath + File.separator + fileName;

			Imgcodecs.imwrite(outputFilePath, destination);
			System.out.println("output file path: " + outputFilePath);
			System.out.println("-----------------------------------------");

		} else {
			String outputFilePath = outputDirPath + File.separator + imageFileName;
			System.out.println(
					"upscaling not needed for image:width x height " + imageFileName + ":" + width + " x " + height);
		}

	}

	


	
}
