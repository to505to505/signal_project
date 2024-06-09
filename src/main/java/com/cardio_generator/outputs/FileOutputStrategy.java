package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code FileOutputStrategy} class is responsible for outputting patient record through local file system,
 * using {@code output}
 */
public class FileOutputStrategy implements OutputStrategy {
    // Used camel case due to the Java variable naming conventions
    private String baseDirectory;
    // Used upper snake case, because it is constant
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Constructor with baseDirectory parameter.
     * @param baseDirectory corresponds to the chosen directory
     */
    public FileOutputStrategy(String baseDirectory) {
        //Adopted changed name
        this.baseDirectory = baseDirectory;
    }

    /**
     * Function that outputs patient record
     * @param patientId corresponds to the patient ID
     * @param timestamp corresponds to the time stamp
     * @param label corresponds to the data label
     * @param data corresponds to the inputted data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            //Adopted changed name
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //Adopted changed name
        String FilePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("%d,  %d, %s, %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}