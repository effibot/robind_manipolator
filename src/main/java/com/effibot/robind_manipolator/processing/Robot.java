package com.effibot.robind_manipolator.processing;

import com.effibot.robind_manipolator.bean.RobotBean;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PShape;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Math.*;
import static processing.core.PConstants.PI;

public class Robot {
    private final RobotBean rb;
    // Theta
    private float[] q = new float[]{0, -PI/2, PI/2, 0, 0, 0};
    // d
    private static final float d1 = 33.0f;
    private static final float d2 = 0.0f;
    private static final float d3 = 0.0f;
    private static final float d4 = 51.0f;
    private static final float d5 = 0.0f;
    private static final float d6 = 12.0f;
    private static final float[] d = new float[]{d1, d2, d3, d4, d5, d6};
    // Alpha
    private static final float alpha1 = -PI / 2;
    private static final float alpha2 = 0.0f;
    private static final float alpha3 = PI / 2;
    private static final float alpha4 = -PI / 2;
    private static  final float alpha5 = PI / 2;
    private static final float alpha6 = 0;
    private static final float[] alpha = new float[]{alpha1, alpha2, alpha3, alpha4, alpha5, alpha6};
    // a
    private static final float a1 = 0.0f;
    private static final float a2 = 50.0f;
    private static final float a3 = 0.0f;
    private static final float a4 = 0.0f;
    private static final float a5 = 0.0f;
    private static final float a6 = 0.0f;
    private static final float[] a = new float[]{a1, a2, a3, a4, a5, a6};
    // displacement for first link
//    private final float offset1 = 29.0f;
//    Base point
    // DH Table
    private Vector<Vector<Float>> dhTable = new Vector<>();
    // Model storage
    private final ArrayList<PShape> shapeList = new ArrayList<>();
    // Frame storage
    private ArrayList<Reference> referenceList = new ArrayList<>();

    // Processing reference
    private final ProcessingBase p3d;

    private static final Logger LOGGER = LoggerFactory.getLogger(Robot.class.getName());
    public Robot(ProcessingBase p3d, RobotBean rb) {
        this.p3d = p3d;
        this.shapeList.add(loadLink(0));
        ListProperty<Float> qJoint = new SimpleListProperty<>();
        qJoint.bind(rb.qProperty());
        qJoint.addListener((observableValue, oldValue, newValue) -> {
            for(int i = 0; i < 6; i++){
                setJoint(i,newValue.get(i));
            }
        });
        for (int i = 0; i < 6; i++) {
            // load link_i
            this.shapeList.add(loadLink(i + 1));
            // Assign DH table
            Vector<Float> dhRow = new Vector<>(Arrays.asList(qJoint.get(i), d[i], alpha[i], a[i]));
            dhTable.add(dhRow);
        }
        this.rb = rb;

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
        // scalefactor for shapes
        float scaleFactor;
        if (id == 0) {
            scaleFactor = 9f;
        } else {
            scaleFactor = 1;
        }
        shape.scale(scaleFactor);
        return shape;
    }

    public void drawLink() {
        shapeList.get(0).setFill(p3d.color(102,51,0));
        //! Offset dalla mappa 9.5

        p3d.pushMatrix();
        p3d.rotateX(PI/2);
        //rotateY(PI/2);
        // Rover
        p3d.shape(shapeList.get(0));
        p3d.popMatrix();
        // dh 01
        Vector<Float> r01 = dhTable.get(0);
        dh(r01.get(0), r01.get(1), r01.get(2), r01.get(3));
        shapeList.get(1).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        //show(0,dx,0,true);
        p3d.rotateY(-PI/2);
        p3d.rotateZ(PI);
        p3d.translate(0, -25, 0);
        p3d.shape(shapeList.get(1));
        p3d.popMatrix();
        // dh 12
        Vector<Float> r12 =dhTable.get(1);
        dh(r12.get(0), r12.get(1), r12.get(2), r12.get(3));
        shapeList.get(2).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
        p3d.translate(-50,0,0);
        p3d.rotateY(PI/2);
        p3d.shape(shapeList.get(2));
        p3d.popMatrix();
        // dh 23
        Vector<Float> r23 =dhTable.get(2);
        dh(r23.get(0), r23.get(1), r23.get(2), r23.get(3));
        shapeList.get(3).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(this,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.rotateZ(PI/2);
        p3d.rotateX(-PI);
        p3d.shape(shapeList.get(3));
        p3d.popMatrix();
        // dh 34
        Vector<Float> r34 =  dhTable.get(3);
         dh(r34.get(0), r34.get(1), r34.get(2), r34.get(3));
        shapeList.get(4).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);

        p3d.translate(0, 51, 0);
        p3d.rotateX(-PI/2);
        p3d.rotateZ(PI/2);
        p3d.shape(shapeList.get(4));
        p3d.popMatrix();
        // dh 45
        Vector<Float> r45 =  dhTable.get(4);
         dh(r45.get(0), r45.get(1), r45.get(2), r45.get(3));
        shapeList.get(5).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, 0, -1);
        p3d.rotateZ(PI/2);
        p3d.shape(shapeList.get(5));
        p3d.popMatrix();
        // dh 56
        Vector<Float> r56 =  dhTable.get(5);
         dh(r56.get(0), r56.get(1), r56.get(2), r56.get(3));
        shapeList.get(6).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        //show(0,0,0,true);
        p3d.translate(0, 0, -13);
        p3d.shape(shapeList.get(6));
        p3d.popMatrix();
    }

    public Vector<Float> getDHrow(int rIndex) {
        return this.dhTable.get(rIndex);
    }
    public Vector<Vector<Float>> getDhTable(){
        return this.dhTable;
    }
    public void setDhTable(Vector<Vector<Float>> newTable) { this.dhTable = newTable;}
    public void setDhTable(float[] joints){
        int id = 0;
        for(float qi : joints){
            this.setJoint(id, qi);
            id +=1;
        }
    }
    public void setJoint(int rIndex, float qNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
//        tempRow.set(0, tempRow.get(0) + qNew);
        tempRow.set(0,qNew);
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
    public List<PShape> getShapeList(){
        return this.shapeList;
    }



    public float[] inverseKinematics(float xdes, float ydes, float zdes,float roll, float pitch, float yaw, int elbow) {

        /* inversa di posizione */
        // posizione del polso
        float xh = (float) (xdes - d6*cos(roll)*sin(pitch));
        float yh = (float) (ydes - d6*sin(roll)*sin(pitch));
        float zh = (float) (zdes - d6*cos(pitch));
        // setups for next calculations
        float b2 = (float) (-elbow*sqrt(pow(xh,2)+pow(yh,2)));
        float b1 = d1-zh;

        // sin(q[3])
        float ds3 = 2*a2*d4;
        float ns3 = (float) (pow(b1,2)+pow(yh,2)+pow(xh,2)-pow(a2,2)-pow(d4,2));
        float s3 = (ns3)/ds3;
        // cos(q[3])
        float c3 = (float) (elbow*sqrt(1-pow(s3,2)));
        // q[3]
        float q3 = (float) atan2((s3),(c3));
        // setups for next calculations
        float A11 = -d4*c3;
        float A12 = a2+d4*s3;
        float A21 = -A12;
        float A22 = A11;
        float det = A11*A22-A12*A21;
        float c2 = -(A12*b2-A22*b1)/det ;
        float s2 = (A11*b2-A21*b1)/det;
        float q2 = (float) atan2(s2,c2);
        //// another setup
        float k = a2*c2+d4*(s2*c3+c2*s3);
        // cos(q[1])
        float c1 = xh/k;
        // sin(q[1])
        float s1 = yh/k;
        // q[1]
        float q1 = (float) atan2((s1),(c1));
        /* inversa di orientamento */
        // Setup for next calculations
        RealMatrix zRoll = MatrixUtils.createRealMatrix(rotateZm(roll));
        RealMatrix yPitch = MatrixUtils.createRealMatrix(rotateYm(pitch));
        RealMatrix zYaw = MatrixUtils.createRealMatrix(rotateZm(yaw));
        //RealMatrix xYaw = MatrixUtils.createRealMatrix(rotateXm(yaw));
        RealMatrix Rdes = zRoll.multiply(yPitch).multiply(zYaw);
        Vector<Vector<Float>> dh03 = new Vector<>();
        dh03.add(getDhTable().get(0));
        dh03.add(getDhTable().get(1));
        dh03.add(getDhTable().get(2));
        double[][] dhValue = dhValue(dh03);
        RealMatrix Q03 = MatrixUtils.createRealMatrix(dhValue);
        RealMatrix R03T = Q03.getSubMatrix(0,2,0,2).transpose();
        RealMatrix R = R03T.multiply(Rdes);
        printR(R);
        // q[5]
        float c5 = (float)R.getEntry(2,2);
        float s5 = (float) (elbow*sqrt(1-pow(c5,2)));
        float q5 = (float) atan2(s5,c5);
        // q[4]
        float c4 =  (float)R.getEntry(0,2)/s5;
        float s4 =  (float)R.getEntry(1,2)/s5;
        float q4 = (float) atan2(s4,c4);
        // q[6]
        float c6 =  (float)-R.getEntry(2,0)/s5;
        float s6 =  (float)R.getEntry(2,1)/s5;
        float q6 = (float) atan2(s6,c6);
        return new float[]{q1,q2,q3,q4,q5,q6};
    }
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
    private void printR(RealMatrix realMatrix){
        LOGGER.info("\n[{},{},{}]\n[{},{},{}]\n[{},{},{}]",
                realMatrix.getEntry(0,0),realMatrix.getEntry(0,1),realMatrix.getEntry(0,2),
                realMatrix.getEntry(1,0),realMatrix.getEntry(1,1),realMatrix.getEntry(1,2),
                realMatrix.getEntry(2,0),realMatrix.getEntry(2,1),realMatrix.getEntry(2,2));
    }
    public double [][] translateM(float x, float y, float z){
        return new double[][]{{x},{y},{z}};
    }
    public double[][] rotor(String axis, float theta, float d){
        double[][] av = new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        double[][] rotationMatrix = new double[][]{};
        double[][] trasformMatrix= new double[][]{};
        if(axis.equals("x")) {
            rotationMatrix = rotateXm(theta);
            trasformMatrix = translateM(d,0,0);
        } else if (axis.equals("z")){
            rotationMatrix = rotateZm(theta);
            trasformMatrix = translateM(0,0,d);
        }
        for(int i = 0; i < 3; i++){
            System.arraycopy(rotationMatrix[i], 0, av[i], 0, 3);
        }
        for(int k = 0; k < 3; k++){
            av[k][3] = trasformMatrix[k][0];
        }
        return av;
    }
    public double[][] dhValue(Vector<Vector<Float>> dhTable){
        double[][] value = new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        RealMatrix qMatrix = MatrixUtils.createRealIdentityMatrix(4);
        for (Vector<Float> dhRow : dhTable) {
            float qValue = dhRow.get(0);
            float dValue = dhRow.get(1);
            float alphaValue = dhRow.get(2);
            float aValue = dhRow.get(3);
            double[][] qZi = rotor("z", qValue, dValue);
            double[][] qXi = rotor("x", alphaValue, aValue);
            RealMatrix qziM = MatrixUtils.createRealMatrix(qZi);
            RealMatrix qxiM = MatrixUtils.createRealMatrix(qXi);
            qMatrix = qMatrix.multiply(qziM).multiply(qxiM);
        }
        for(int i =0; i < 4; i++){
            value[i] = qMatrix.getRow(i);
        }
        return value;
    }



}