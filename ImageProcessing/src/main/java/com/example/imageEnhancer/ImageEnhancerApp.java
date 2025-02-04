package com.example.imageEnhancer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ImageEnhancerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImageEnhancerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Define the input and output directories
        String inputDirPath = "C:\\Users\\aatif\\Desktop\\bulk enhance test";
        String outputDirPath = "C:\\Users\\aatif\\Desktop\\bulk enhance test\\enhanced";
        File inputDir = new File(inputDirPath);
        File outputDir = new File(outputDirPath);

        // Create the output directory if it does not exist
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Process each image in the input directory
        for (File file : inputDir.listFiles()) {
            if (file.isFile()) {
                enhanceImage(file.getAbsolutePath(), new File(outputDir, file.getName()).getAbsolutePath());
            }
        }

        System.out.println("Image enhancement complete!");
    }

    private void enhanceImage(String inputImagePath, String outputImagePath) throws IOException, InterruptedException {
        // Build the command to run the Python script
        ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\aatif\\Downloads\\imageUpscaler\\imageUpscaler\\src\\main\\java\\com\\example\\imageEnhancer\\enhance_image.py", inputImagePath, outputImagePath);
        pb.inheritIO();
        // Start the process and wait for it to finish
        Process process = pb.start();
        process.waitFor();
    }
}
