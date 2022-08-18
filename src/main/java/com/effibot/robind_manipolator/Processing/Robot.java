package com.effibot.robind_manipolator.Processing;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import processing.core.PShape;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import static java.lang.Math.*;
import static processing.core.PConstants.PI;

public class Robot {
    // Theta
    private float[] q = new float[]{0, PI/2, -PI/2, PI, 0, 0};
    // d
    private final float d1 = 33.0f;
    private final float d2 = 0.0f;
    private final float d3 = 0.0f;
    private final float d4 = 51.0f;
    private final float d5 = 0.0f;
    private final float d6 = 12.0f;
    private final float[] d = new float[]{d1, d2, d3, d4, d5, d6};
    // Alpha
    private final float alpha1 = PI / 2;
    private final float alpha2 = 0.0f;
    private final float alpha3 = -PI / 2;
    private final float alpha4 = PI / 2;
    private final float alpha5 = -PI / 2;
    private final float alpha6 = 0;
    private final float[] alpha = new float[]{alpha1, alpha2, alpha3, alpha4, alpha5, alpha6};
    // a
    private final float a1 = 0.0f;
    private final float a2 = 50.0f;
    private final float a3 = 0.0f;
    private final float a4 = 0.0f;
    private final float a5 = 0.0f;
    private final float a6 = 0.0f;
    private final float[] a = new float[]{a1, a2, a3, a4, a5, a6};
    // displacement for first link
    private final float offset1 = 29.0f;
    // DH Table
    private Vector<Vector<Float>> dhTable = new Vector<>();
    // Model storage
    private final ArrayList<PShape> shapeList = new ArrayList<>();
    // Frame storage
    private ArrayList<Reference> referenceList = new ArrayList<>();
    // scalefactor for shapes
    private float scaleFactor;

    // Processing reference
    private ProcessingBase p3d;
    // Observers
    public Robot(ProcessingBase p3d) {
        this.p3d = p3d;
        this.shapeList.add(loadLink(0));
        for (int i = 0; i < 6; i++) {
            // load link_i
            this.shapeList.add(loadLink(i + 1));
            // Assign DH table
            Vector<Float> dhRow = new Vector<>(Arrays.asList(q[i], d[i], alpha[i], a[i]));
            dhTable.add(dhRow);
        }
    }

    public void dh(float theta, float d, float alpha, float a) {
        p3d.rotateZ(theta);
        p3d.translate(0, 0, d);
        p3d.rotateX(alpha);
        p3d.translate(a, 0, 0);
    }

    private PShape loadLink(int id) {
        File file = p3d.sketchFile("models/r" + id + "c.obj");
        String objPath = file.getAbsolutePath();
        PShape shape = p3d.loadShape(objPath);
        if (id == 0) {
//            scaleFactor = 30.0f;
            scaleFactor = 9f;
        } else {
            scaleFactor = 1;
        }
        shape.scale(scaleFactor);
        return shape;
    }

    public void drawLink() {
        this.getShapeList().get(0).setFill(p3d.color(102,51,0));
        //! Offset dalla mappa 9.5        
        p3d.pushMatrix();
        p3d.rotateX(PI/2);
        p3d.rotateY(PI/2);
        p3d.shape(this.getShapeList().get(0));
        p3d.popMatrix();
        // dh 01
        Vector<Float> r01 = this.getDhTable().get(0);
        this.dh(r01.get(0), r01.get(1), r01.get(2), r01.get(3));
        this.getShapeList().get(1).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, -25, 0);
        p3d.rotateY(PI/2);
        p3d.shape(this.getShapeList().get(1));
        p3d.popMatrix();
        // dh 12
        Vector<Float> r12 = this.getDhTable().get(1);
        this.dh(r12.get(0), r12.get(1), r12.get(2), r12.get(3));
        this.getShapeList().get(2).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
        p3d.translate(-50,0,0);
        p3d.rotateY(PI/2);
        p3d.shape(this.getShapeList().get(2));
        p3d.popMatrix();
        // dh 23
        Vector<Float> r23 = this.getDhTable().get(2);
        this.dh(r23.get(0), r23.get(1), r23.get(2), r23.get(3));
        this.getShapeList().get(3).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(this,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.rotateZ(PI/2);
        p3d.rotateX(-PI);
        p3d.shape(this.getShapeList().get(3));
        p3d.popMatrix();
        // dh 34
        Vector<Float> r34 = this.getDhTable().get(3);
        this.dh(r34.get(0), r34.get(1), r34.get(2), r34.get(3));
        this.getShapeList().get(4).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, -51, 0);
        p3d.rotateX(PI/2);
        p3d.rotateZ(PI/2);
        p3d.shape(this.getShapeList().get(4));
        p3d.popMatrix();
        // dh 45
        Vector<Float> r45 = this.getDhTable().get(4);
        this.dh(r45.get(0), r45.get(1), r45.get(2), r45.get(3));
        this.getShapeList().get(5).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, 0, -1);
        p3d.rotateZ(PI/2);
        p3d.shape(this.getShapeList().get(5));
        p3d.popMatrix();
        // dh 56
        Vector<Float> r56 = this.getDhTable().get(5);
        this.dh(r56.get(0), r56.get(1), r56.get(2), r56.get(3));
        this.getShapeList().get(6).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, 0, -13);
        p3d.shape(this.getShapeList().get(6));
        p3d.popMatrix();
    }

    public Vector<Float> getDHrow(int rIndex) {
        return this.dhTable.get(rIndex);
    }
    public Vector<Vector<Float>> getDhTable(){
        return this.dhTable;
    }
    public void setDhTable(Vector<Vector<Float>> newTable) { this.dhTable = newTable;}
    public void setJoint(int rIndex, float qNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
        tempRow.set(0, tempRow.get(0) + qNew);
        this.dhTable.set(rIndex, tempRow);
    }
    public void setDistance(int rIndex, int cIndex, float dNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
        tempRow.set(cIndex, tempRow.get(cIndex) + dNew);
        this.dhTable.set(rIndex, tempRow);
    }

    public PShape getShape(int id) {
        return this.shapeList.get(id);
    }
    public ArrayList<PShape> getShapeList(){
        return this.shapeList;
    }


    private float xdes, ydes, zdes = 0;
    private float roll, pitch, yaw = 0;
    private double[] inverseKinematics(int elbow) {
        /* inversa di posizione */
        // posizione del polso
        double xh = xdes + d6*cos(roll)*sin(pitch);
        double yh = ydes - d6*sin(roll)*cos(pitch);
        double zh = - d6*cos(pitch);
        // setups for next calculations
        double A = elbow*sqrt(pow(xh,2)+pow(yh,2));
        double B = zh-d1;
        // sin(q[3])
        double ds3 = 2*a2*d4;
        double ns3 = pow(B,2)+pow(yh,2)+pow(xh,2)-pow(a2,2)-pow(d4,2);
        double s3 = -(ns3)/ds3;
        // cos(q[3])
        double c3 = elbow*sqrt(1-pow(s3,2));
        // q[3]
        double q3 = atan2(s3,c3);
        // setups for next calculations
        double C = a2-d4*s3;
        double D = d4*c3;
        double det = pow(C,2)+pow(D,2);
        // cos(q[2])
        double c2 = (D*B+A*C)/det;
        // sin(q[2])
        double s2 = (-D*A+C*B)/det;
        // q[2]
        double q2 = atan2(s2,c2);
        // another setup
        double k = a2*c2-d4*sin(q2+q3);
        // cos(q[1])
        double c1 = xh/k;
        // sin(q[1])
        double s1 = yh/k;
        // q[1]
        double q1 = atan2(s1,c1);
        /* inversa di orientamento */
        // Setup for next calculations
        RealMatrix zRoll = MatrixUtils.createRealMatrix(rotateZm(roll));
        RealMatrix zPitch = MatrixUtils.createRealMatrix(rotateYm(pitch));
        RealMatrix zYaw = MatrixUtils.createRealMatrix(rotateZm(yaw));
        RealMatrix R = zRoll.multiply(zPitch).multiply(zYaw);
        // q[5]
        double c5 = R.getEntry(3,3);
        double s5 = elbow*sqrt(1-pow(c5,2));
        double q5 = atan2(s5,c5);
        // q[4]
        double c4 = -R.getEntry(1,3)/s5;
        double s4 = -R.getEntry(2,3)/s5;
           double q4 = atan2(s4,c4);
        // q[6]
        double c6 = R.getEntry(3,1)/s5;
        double s6 = -R.getEntry(3,2)/s5;
        double q6 = atan2(s6,c6);

        return new double[]{q1,q2,q3,q4,q5,q6};
    }

    private double [][] rotateZm(double theta){
        return new double[][]{{cos(theta), sin(theta), 0},
                            {sin(-theta), cos(theta), 0},
                            {0d, 0d, -1d}};
    }
    private double [][] rotateYm(double theta){
        return new double[][]{{cos(theta), 0, sin(theta)},
                {0d, -1d, 0d},
                {-sin(theta),0, cos(theta)}};
    }
}
