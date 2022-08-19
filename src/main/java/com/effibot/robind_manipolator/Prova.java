package com.effibot.robind_manipolator;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Vector;

import static processing.core.PApplet.*;

public class Prova {
    private double [][] rotateZm(float theta){
        return new double[][]{{cos(theta), -sin(theta), 0},
                {sin(theta), cos(theta), 0},
                {0d, 0d, 1}};
    }
    private double [][] rotateYm(float theta){
        return new double[][]{{cos(theta), 0, sin(theta)},
                {0d, 1d, 0d},
                {-sin(theta),0, cos(theta)}};
    }
    private double [][] rotateXm(float theta){
        return new double[][]{{1,0,0},{0,cos(theta), -sin(theta)},{0,sin(theta), cos(theta)}};
    }
    public double [][] translateM(float x, float y, float z){
        return new double[][]{{x},{y},{z}};
    }
    public double[][] rotor(String axis, float theta, float d){
        float theta_r = radians(theta);
        double[][] av = new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        double[][] R = new double[][]{};
        double[][] T= new double[][]{};
        if(axis.equals("x")) {
            R = rotateXm(theta_r);
            T = translateM(d,0,0);
        } else if (axis.equals("z")){
            R = rotateZm(theta_r);
            T = translateM(0,0,d);
        }
        for(int i = 0; i < 3; i++){
            System.arraycopy(R[i], 0, av[i], 0, 3);
        }
        for(int k = 0; k < 3; k++){
            av[k][3] = T[0][k];
        }
        return av;
    }
    public double[][] dhValue(Vector<Vector<Float>> dhTable){
        double[][] value = new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        RealMatrix Q = MatrixUtils.createRealIdentityMatrix(4);
        for (Vector<Float> dhRow : dhTable) {
            float q = dhRow.get(0);
            float d = dhRow.get(1);
            float alpha = dhRow.get(2);
            float a = dhRow.get(3);
            double[][] Qzi = rotor("z", q, d);
            double[][] Qxi = rotor("x", alpha, a);
            RealMatrix Qzim = MatrixUtils.createRealMatrix(Qzi);
            RealMatrix Qxim = MatrixUtils.createRealMatrix(Qxi);

            Q = Q.multiply(Qzim).multiply(Qxim);
        }
        for(int i =0; i < 4; i++){
            value[i] = Q.getRow(i);
        }
        return value;
    }
}
