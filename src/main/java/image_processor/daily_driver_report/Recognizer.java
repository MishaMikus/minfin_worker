package image_processor.daily_driver_report;

import org.bytedeco.javacpp.opencv_core;
import org.opencv.core.Core;

import java.io.File;

import static org.bytedeco.javacpp.helper.opencv_imgcodecs.cvLoadImage;

public class Recognizer {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        //load
        File inFile = new File("F:\\viber_image_2019-11-20_16-20-36.jpg");
        opencv_core.IplImage inputImage = cvLoadImage(inFile.getAbsolutePath());

        //downscale
        int percent = 10;
        System.out.println("h:" + inputImage.height() + "\tw:" + inputImage.width());


    }
}
