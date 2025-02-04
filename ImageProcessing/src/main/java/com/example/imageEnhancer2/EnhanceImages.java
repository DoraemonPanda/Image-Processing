package com.example.imageEnhancer2;

import java.io.File;

public class EnhanceImages {
    public static void main(String[] args) {
        String pythonScriptPath = "path_to/enhance_images.py";
        String inputDirectory = "path_to/input_directory";
        String outputDirectory = "path_to/output_directory";

        try {
            // Construct the command
            String[] command = new String[]{
                "python", pythonScriptPath, inputDirectory, outputDirectory
            };

            // Start the process
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            // Check the exit status
            int exitStatus = process.exitValue();
            if (exitStatus == 0) {
                System.out.println("Images enhanced successfully.");
            } else {
                System.out.println("Error occurred while enhancing images.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
