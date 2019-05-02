package com.johnpickup;

import com.johnpickup.garmin.converter.CourseConverter;
import com.johnpickup.garmin.fit.FitSaver;
import com.johnpickup.garmin.route.Course;
import com.johnpickup.gpx.GpxReader;
import com.johnpickup.gpx.GpxType;
import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class GarminRouteGenerator {
    private GpxReader gpxReader = new GpxReader();
    private CourseConverter courseConverter = new CourseConverter();

    public static void main(String[] args) {
        try {
            GarminRouteGenerator garminRouteGenerator = new GarminRouteGenerator();
            for (String arg : args) {
                garminRouteGenerator.convert(arg);
            }
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void convert(String inputFilename) throws JAXBException, FileNotFoundException {
        String outputFilename = FilenameUtils.removeExtension(inputFilename) + ".fit";
        GpxType gpxType = gpxReader.readGpxFile(inputFilename);
        Course convertedCourse = courseConverter.convert(gpxType);
        FitSaver.save(convertedCourse, outputFilename);
        System.out.println(String.format("Converted %s to %s", inputFilename, outputFilename));
    }
}
