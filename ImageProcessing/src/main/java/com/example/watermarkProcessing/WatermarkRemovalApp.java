package com.example.watermarkProcessing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class WatermarkRemovalApp implements CommandLineRunner {

	static {
		//System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadLocally();

	}

    public static void main(String[] args) {
        SpringApplication.run(WatermarkRemovalApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Specify the input and output directories
        String inputDirPath = "C:\\Users\\aatif\\Desktop\\test\\watermarkRemoval";
        String outputDirPath = "C:\\Users\\aatif\\Desktop\\test\\watermarkRemoval\\watermark removed";
        File inputDir = new File(inputDirPath);
        File outputDir = new File(outputDirPath);

        // Create output directory if it does not exist
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Process each image in the input directory
        for (File file : inputDir.listFiles()) {
            if (file.isFile()) {
                removeWatermark(file, new File(outputDir, file.getName()));
            }
        }

        System.out.println("Watermark removal complete!");
    }

    private void removeWatermark(File inputFile, File outputFile) {
        // Load the image
        Mat src = Imgcodecs.imread(inputFile.getAbsolutePath());

        // Apply a Gaussian blur to the entire image to remove watermarks
        Mat blurred = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);
        Imgproc.GaussianBlur(src, blurred, new Size(45, 45), 0);

        // Perform a weighted blend of the original and the blurred image
        Mat result = new Mat(src.rows(), src.cols(), CvType.CV_8UC3);
        Core.addWeighted(src, 1.5, blurred, -0.5, 0, result);

        // Save the output image to the specified file
        Imgcodecs.imwrite(outputFile.getAbsolutePath(), result);
    }
}

