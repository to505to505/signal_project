package com.main;

import com.cardio_generator.HealthDataSimulator;
import com.cardio_generator.generators.DataAccess;
import com.data_management.DataStorage;
//import org.junit.Test;

import java.io.IOException;

public class Main {
    //@Test
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataAccess")) {
            DataAccess.main(new String[]{});
        }
        else if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            HealthDataSimulator.main(new String[]{});
        }
    }
}