package com.johnpickup.garmin.fit;

import com.garmin.fit.FileEncoder;
import com.garmin.fit.Fit;
import com.johnpickup.garmin.fit.FitGenerator;

import java.io.File;

public class FitSaver {

    public static void save(FitGenerator fitGenerator, String filename) {
        save(fitGenerator, new java.io.File(filename));
    }

    public static void save(FitGenerator fitGenerator, File file) {
        FileEncoder encode = new FileEncoder( file, Fit.ProtocolVersion.V1_0);
        encode.write(fitGenerator.generate());
        encode.close();
    }
}