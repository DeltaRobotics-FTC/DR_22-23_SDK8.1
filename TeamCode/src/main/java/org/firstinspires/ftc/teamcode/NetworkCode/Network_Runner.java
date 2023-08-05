package org.firstinspires.ftc.teamcode.NetworkCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Network_Runner")
//@Disabled

public class Network_Runner extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException{

        telemetry.addData("output","nothing");
        telemetry.update();

        waitForStart();

        try{
            telemetry.addData("output","nothing2");
            telemetry.update();

            File myObj = new File("Json_Reading.json");
            Scanner myReader = new Scanner(myObj);

            boolean weightsf = false;
            int wState = 0;

            ArrayList<Integer> w = new ArrayList<Integer>();
            ArrayList<Integer> w1 = new ArrayList<Integer>();


            int i = 0;
            int j = 0;
            int k = 0;


            int i1 = 0;
            int j1 = 0;
            int k1 = 0;



            ArrayList<Integer> b = new ArrayList<Integer>();
            ArrayList<Integer> b1 = new ArrayList<Integer>();


            boolean biasesf = false;
            int bState = 0;


            while (myReader.hasNextLine()){
                String data = myReader.nextLine();


                if (data.contains("weights")) {
                    weightsf = true;
                }
                if (data.contains("biases")) {
                    weightsf = false;
                }
                if (weightsf && data.contains("[")) {
                    wState++;
                }
                if (weightsf && data.contains("]")) {
                    wState--;
                }
                if ((weightsf && wState == 1) && !data.contains("[")) {
                    w1.add(k);
                    k = 0;
                }
                else if((weightsf && wState == 2) && !data.contains("[")) {
                    w.add(j);
                    j = 0;
                    k++;
                }
                else if((weightsf && wState == 3) && !data.contains("[")) {
                    j++;
                }


                if (data.contains("biases")) {
                    biasesf = true;
                }
                if (data.contains("weights")) {
                    biasesf = false;
                }
                if (biasesf && data.contains("[")) {
                    bState++;
                }
                if (biasesf && data.contains("]")) {
                    bState--;
                }
                if ((biasesf && bState == 1) && !data.contains("[")) {
                    b1.add(k1);
                    k1 = 0;
                }
                else if((biasesf && bState == 2) && !data.contains("[")) {
                    b.add(j1);
                    j1 = 0;
                    k1++;
                }
                else if((biasesf && bState == 3) && !data.contains("[")) {
                    j1++;
                }
            }




            double[][][] weights = new double[w1.size()][][];


            int pw = 0;
            for (i = 0; i < w1.size();i++){
                weights[i] = new double[w1.get(i)][];
                for (j = 0; j < weights[i].length;j++){
                    weights[i][j] = new double[w.get(pw)];
                    pw++;
                }
            }




            double[][][] biases = new double[b1.size()][][];


            pw = 0;
            for (i1 = 0; i1 < b1.size();i1++){
                biases[i1] = new double[b1.get(i1)][];
                for (j1 = 0; j1 < biases[i1].length;j1++){
                    biases[i1][j1] = new double[b.get(pw)];
                    pw++;
                }
            }


            myReader.close();


            File myObj2 = new File("Json_Reading.json");
            Scanner myReader2 = new Scanner(myObj2);


            weightsf = false;
            wState = 0;


            i = 0;
            j = 0;
            k = 0;


            i1 = 0;
            j1 = 0;
            k1 = 0;


            biasesf = false;
            bState = 0;




            while (myReader2.hasNextLine()){
                String data = myReader2.nextLine();


                if (data.contains("weights")) {
                    weightsf = true;
                }
                if (data.contains("biases")) {
                    weightsf = false;
                }
                if (weightsf && data.contains("[")) {
                    wState++;
                }
                if (weightsf && data.contains("]")) {
                    wState--;
                }
                if ((weightsf && wState == 1) && !data.contains("[")) {
                    i++;
                    j = 0;
                }
                else if((weightsf && wState == 2) && !data.contains("[")) {
                    j++;
                    k = 0;
                }
                else if((weightsf && wState == 3) && !data.contains("[")) {
                    weights[i][j][k] = Double.parseDouble((data.replace("                ", "")).replace(",", ""));
                    k++;
                }


                if (data.contains("biases")) {
                    biasesf = true;
                }
                if (data.contains("weights")) {
                    biasesf = false;
                }
                if (biasesf && data.contains("[")) {
                    bState++;
                }
                if (biasesf && data.contains("]")) {
                    bState--;
                }
                if ((biasesf && bState == 1) && !data.contains("[")) {
                    i1++;
                    j1 = 0;
                }
                else if((biasesf && bState == 2) && !data.contains("[")) {
                    j1++;
                    k1 = 0;
                }
                else if((biasesf && bState == 3) && !data.contains("[")) {
                    biases[i1][j1][k1] = Double.parseDouble((data.replace("                ", "")).replace(",", ""));
                    k1++;
                }
            }

            myReader2.close();

            double[][] x = new double[][]{{0.0},{1.0},{2.0},{3.0},{4.0},{5.0},{6.0},{7.0},{8.0},{9.0}};




            //telemetry.addData("Y value",Camera_Test.circle());
            //telemetry.update();
            System.out.println();



            telemetry.addData("output",Network_Transition.predict(weights, biases, x).toString());
            telemetry.update();
            //Network_Transition.predict(weights, biases, x);
            sleep(10000);

        }
        catch (FileNotFoundException e) {
            //System.out.println("An error occurred.");
            //e.printStackTrace();
            telemetry.addData("crash",e);
            telemetry.update();
        }
        sleep(1000);
    }
}




