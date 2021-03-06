package com.johnpickup;

import com.johnpickup.garmin.converter.CourseConverter;
import com.johnpickup.garmin.fit.FitSaver;
import com.johnpickup.garmin.route.Course;
import com.johnpickup.gpx.GpxReader;
import com.johnpickup.gpx.GpxType;
import com.johnpickup.gui.ConverterForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
public class GarminRouteGenerator {
    private GpxReader gpxReader = new GpxReader();
    private CourseConverter courseConverter = new CourseConverter();

    public static void main(String[] args) {
        if (args.length == 0) {
            runGui();
        }

        try {
            GarminRouteGenerator garminRouteGenerator = new GarminRouteGenerator();
            for (String arg : args) {
                garminRouteGenerator.convert(arg);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
        }

    }

    private static void runGui() {
        ConverterForm.display();
    }

    private void convert(String inputFilename) throws JAXBException, FileNotFoundException {
        String outputDir = FilenameUtils.getPath(inputFilename);
        convert(new File(inputFilename), new File(outputDir));
    }

    public void convert(File inputFile, File outputFile) throws JAXBException, FileNotFoundException {
        if (outputFile.isDirectory()) {
            outputFile = new File(outputFile, FilenameUtils.getBaseName(inputFile.getName()) + ".fit");
        }

        log.info("Converting {} to {}", inputFile, outputFile);
        GpxType gpxType = gpxReader.readGpxFile(inputFile);
        Course convertedCourse = courseConverter.convert(gpxType);
        FitSaver.save(convertedCourse, outputFile);
        log.info("Converted {} to {}", inputFile, outputFile);
    }
}
