package org.firstinspires.ftc.teamcode.NetworkCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import java.util.ArrayList;

@Autonomous(name="Camera_Test")

public class Camera_Test extends OpMode {
    static final int STREAM_WIDTH = 400; // modify for your camera
    static final int STREAM_HEIGHT = 400; // modify for your camera
    OpenCvWebcam webcam;
    static SamplePipeline pipeline;

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"); // put your camera's name here
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new SamplePipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Failed","");
                telemetry.update();
            }
        });

    }

    public void loop() {
        telemetry.addData("value", pipeline.getAnalysis());
        telemetry.update();
    }


    //@Override
    public static int circle() {
        return pipeline.getAnalysis();
    }

    class SamplePipeline extends OpenCvPipeline {

        Mat YCrCb = new Mat();
        Mat Y = new Mat();
        int avg;


        /*
         * This function takes the RGB frame, converts to YCrCb,
         * and extracts the Y channel to the 'Y' variable
         */
        void inputToY(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            ArrayList<Mat> yCrCbChannels = new ArrayList<Mat>(3);
            Core.split(YCrCb, yCrCbChannels);
            Y = yCrCbChannels.get(0);

        }

        @Override
        public void init(Mat firstFrame) {
            inputToY(firstFrame);
        }

        @Override
        public Mat processFrame(Mat input) {
            inputToY(input);
            System.out.println("processing requested");
            avg = (int) Core.mean(Y).val[0];
            YCrCb.release(); // don't leak memory!
            Y.release(); // don't leak memory!
            return input;
        }

        public int getAnalysis() {
            return avg;
        }
    }

}
