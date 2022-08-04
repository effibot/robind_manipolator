package com.effibot.robind_manipolator.Processing;
import processing.core.PShape;
import processing.core.PVector;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

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
    private final ProcessingBase p3d;
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

}
