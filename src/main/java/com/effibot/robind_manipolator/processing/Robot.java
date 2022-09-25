package com.effibot.robind_manipolator.processing;

import com.effibot.robind_manipolator.bean.RobotBean;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PShape;
import processing.core.PVector;

import java.io.File;
import java.util.*;

import static java.lang.Math.*;
import static processing.core.PConstants.PI;

public class Robot {
    private final RobotBean rb;
    // Theta
    private float[] q = new float[]{0, -PI / 2, PI / 2, 0, PI/2f, 0};
    // d
    private static final float D_1 = 33.0f;
    private static final float D_2 = 0.0f;
    private static final float D_3 = 0.0f;
    private static final float D_4 = 51.0f;
    private static final float D_5 = 0.0f;
    private static final float D_6 = 12.0f;
    private static final float[] d = new float[]{D_1, D_2, D_3, D_4, D_5, D_6};
    // Alpha
    private static final float ALPHA_1 = -PI / 2;
    private static final float ALPHA_2 = 0.0f;
    private static final float ALPHA_3 = PI / 2;
    private static final float ALPHA_4 = -PI / 2;
    private static final float ALPHA_5 = PI / 2;
    private static final float ALPHA_6 = 0;
    private static final float[] alpha = new float[]{ALPHA_1, ALPHA_2, ALPHA_3, ALPHA_4, ALPHA_5, ALPHA_6};
    // a
    private static final float A_1 = 0.0f;
    private static final float A_2 = 50.0f;
    private static final float A_3 = 0.0f;
    private static final float A_4 = 0.0f;
    private static final float A_5 = 0.0f;
    private static final float A_6 = 0.0f;
    private static final float[] a = new float[]{A_1, A_2, A_3, A_4, A_5, A_6};
    // displacement for first link
//    private final float offset1 = 29.0f;
    private boolean opSpace = false;
    private boolean rotOpSpace = false;
    private boolean toShow = false;
    //    Base point
    // DH Table
    private Vector<Vector<Float>> dhTable = new Vector<>();
    // Model storage
    private final ArrayList<PShape> shapeList = new ArrayList<>();
    // Frame storage
    private ArrayList<Reference> referenceList = new ArrayList<>();

    // Processing reference
    private final ProcessingBase p3d;
    private final ArrayList<Float[]> coords;

    private static final Logger LOGGER = LoggerFactory.getLogger(Robot.class.getName());
    private final boolean toBind;
    // joint binding
    private final ListProperty<Float> qJoint = new SimpleListProperty<>(FXCollections.observableArrayList(
            Arrays.asList(
                    ArrayUtils.toObject(
                            new float[]{0f, -PI / 2, PI / 2, 0f, PI/2f, 0f}
                    ))
    ));

    public Robot(ProcessingBase p3d, RobotBean rb, boolean toBind) {
        this.toBind = toBind;
        this.p3d = p3d;
        this.shapeList.add(loadLink(0));
        this.rb = rb;
        this.coords = convert();
        if (toBind) bind();
        for (int i = 0; i < 6; i++) {
            // load link_i
            this.shapeList.add(loadLink(i + 1));
            // Assign DH table
            Vector<Float> dhRow = new Vector<>(Arrays.asList(qJoint.get(i), d[i], alpha[i], a[i]));
            dhTable.add(dhRow);
        }


    }

    private void bind() {
        qJoint.bind(rb.qProperty());
        qJoint.addListener((observableValue, oldValue, newValue) -> {
            for (int i = 0; i < 6; i++) {
                setJoint(i, newValue.get(i));
            }
        });
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
        if (opSpace && toShow) {
            for (Float[] cord : coords) {
                p3d.pushMatrix();
                p3d.translate(cord[0], cord[1], cord[2]);
                p3d.stroke(255, 50);
                p3d.strokeWeight(1);
                p3d.noFill();
                p3d.sphere(D_6);
                p3d.popMatrix();
            }
        }
        shapeList.get(0).setFill(p3d.color(102, 51, 0));
        //! Offset dalla mappa 9.5
        p3d.pushMatrix();
        p3d.rotateX(PI / 2);
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
        p3d.rotateY(-PI / 2);
        p3d.rotateZ(PI);
        p3d.translate(0, -25, 0);
        p3d.shape(shapeList.get(1));
        p3d.popMatrix();
        // draw opSpace
        if (opSpace && toShow) {
            p3d.stroke(125);
            p3d.strokeWeight(1);
            p3d.noFill();
            p3d.sphere(A_2 + D_4);
            p3d.noStroke();
        }

        // dh 12
        Vector<Float> r12 = dhTable.get(1);
        dh(r12.get(0), r12.get(1), r12.get(2), r12.get(3));
        shapeList.get(2).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
        p3d.translate(-50, 0, 0);
        p3d.rotateY(PI / 2);
        p3d.shape(shapeList.get(2));
        p3d.popMatrix();
        // dh 23
        Vector<Float> r23 = dhTable.get(2);
        dh(r23.get(0), r23.get(1), r23.get(2), r23.get(3));
        shapeList.get(3).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(this,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.rotateZ(PI / 2);
        p3d.rotateX(-PI);
        p3d.shape(shapeList.get(3));
        p3d.popMatrix();
        // dh 34
        Vector<Float> r34 = dhTable.get(3);
        dh(r34.get(0), r34.get(1), r34.get(2), r34.get(3));
        shapeList.get(4).setFill(p3d.color(40, 2, 100));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);

        p3d.translate(0, 51, 0);
        p3d.rotateX(-PI / 2);
        p3d.rotateZ(PI / 2);
        p3d.shape(shapeList.get(4));
        p3d.popMatrix();

        // draw rotOpSpace
        if (rotOpSpace && toShow) {
            p3d.stroke(0);
            p3d.strokeWeight(1);
            p3d.noFill();
            p3d.sphere(D_6);
            p3d.noStroke();
        }
        // dh 45
        Vector<Float> r45 = dhTable.get(4);
        dh(r45.get(0), r45.get(1), r45.get(2), r45.get(3));
        shapeList.get(5).setFill(p3d.color(100, 200, 0));
        p3d.pushMatrix();
//        zeroFrame = new Reference(p3d,new PVector(0,0,0));
//        zeroFrame.show(true);
        p3d.translate(0, 0, -1);
        p3d.rotateZ(PI / 2);
        p3d.shape(shapeList.get(5));
        p3d.popMatrix();
        // dh 56
        Vector<Float> r56 = dhTable.get(5);
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

    public Vector<Vector<Float>> getDhTable() {
        return this.dhTable;
    }

    public void setDhTable(Vector<Vector<Float>> newTable) {
        this.dhTable = newTable;
    }

    public void setDhTable(float[] joints) {
        int id = 0;
        for (float qi : joints) {
            this.setJoint(id, qi);
            q[id] = qi;
            id += 1;
        }
    }

    public void setJoint(int rIndex, float qNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
//        tempRow.set(0, tempRow.get(0) + qNew);
        tempRow.set(0, qNew);
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

    public List<PShape> getShapeList() {
        return this.shapeList;
    }


    public float[] inverseKinematics(float xdes, float ydes, float zdes, float roll, float pitch, float yaw, int elbow) {

        /* inversa di posizione */
        RealMatrix RDes = RobotUtils.rotZYX(roll,pitch,yaw);
//        RealVector d1 = MatrixUtils.createRealVector(new double[]{});
        // posizione del polso
        float xh = (float) (xdes - D_6 * (sin(roll*sin(yaw)+cos(roll)*sin(pitch)*cos(yaw))));
        float yh = (float) (ydes - D_6 * (sin(roll) * sin(pitch)*cos(yaw)-cos(roll)*sin(yaw)));
        float zh = (float) (zdes - D_6 * cos(pitch)*cos(yaw));
        // setups for next calculations
        float b2 = (float) (-elbow * sqrt(pow(xh, 2) + pow(yh, 2)));
        float b1 = D_1 - zh;

        // sin(q[3])
        float ds3 = 2 * A_2 * D_4;
        float ns3 = (float) (pow(b1, 2) + pow(yh, 2) + pow(xh, 2) - pow(A_2, 2) - pow(D_4, 2));
        float s3 = (ns3) / ds3;
        // cos(q[3])
        float c3 = (float) (elbow * sqrt(1 - pow(s3, 2)));
        // q[3]
        float q3 = (float) atan2((s3), (c3));
        // setups for next calculations
        float A11 = -D_4 * c3;
        float A12 = A_2 + D_4 * s3;
        float A21 = -A12;
        float A22 = A11;
        float det = A11 * A22 - A12 * A21;
        float c2 = -(A12 * b2 - A22 * b1) / det;
        float s2 = (A11 * b2 - A21 * b1) / det;
        float q2 = (float) atan2(s2, c2);
        //// another setup
        float k = A_2 * c2 + D_4 * (s2 * c3 + c2 * s3);
        // cos(q[1])
        float c1 = xh / k;
        // sin(q[1])
        float s1 = yh / k;
        // q[1]
        float q1 = (float) atan2((s1), (c1));
        /* inversa di orientamento */
        // Setup for next calculations
        Vector<Vector<Float>> dh03 = new Vector<>();
        dh03.add(getDhTable().get(0));
        dh03.add(getDhTable().get(1));
        dh03.add(getDhTable().get(2));
        double[][] dhValue = RobotUtils.dhValue(dh03);
        RealMatrix Q03 = MatrixUtils.createRealMatrix(dhValue);
        RealMatrix R03T = Q03.getSubMatrix(0, 2, 0, 2).transpose();
        RealMatrix R = R03T.multiply(RDes);
//        printR(R);
        // q[5]
        float c5 = (float) R.getEntry(2, 2);
        float s5 = (float) (elbow * sqrt(1 - pow(c5, 2)));
        float q5 = (float) atan2(s5, c5);
        // q[4]
        float c4 = (float) R.getEntry(0, 2) / s5;
        float s4 = (float) R.getEntry(1, 2) / s5;
        float q4 = (float) atan2(s4, c4);
        // q[6]
        float c6 = (float) -R.getEntry(2, 0) / s5;
        float s6 = (float) R.getEntry(2, 1) / s5;
        float q6 = (float) atan2(s6, c6);
        return new float[]{q1, q2, q3, q4, q5, q6};
    }


    public boolean isOpSpace() {
        return opSpace;
    }

    public void setOpSpace(boolean opSpace) {
        this.opSpace = opSpace;
    }

    public boolean isRotOpSpace() {
        return rotOpSpace;
    }

    public void setRotOpSpace(boolean rotOpSpace) {
        this.rotOpSpace = rotOpSpace;
    }

    public boolean isToShow() {
        return toShow;
    }

    public void setToShow(boolean toShow) {
        this.toShow = toShow;
    }

    public void showSpace(boolean b) {
        setOpSpace(b);
        setToShow(b);
        setRotOpSpace(b);
    }

    ArrayList<Float[]> convert() {
        // convert string into list of float vectors
        ArrayList<Float[]> list = new ArrayList<>();
        String[] st = p3d.loadStrings("src/main/java/com/effibot/robind_manipolator/processing/data/file.txt");
        for (String s : st) {
            String[] result = s.split(",");
            Float[] bc = new Float[3];
            for (int i = 0; i < result.length; i++) {
                bc[i] = Float.valueOf(result[i]);
            }
            list.add(bc);
        }
        return list;
    }

    public static float[] getD() {
        return new float[]{D_1, D_2, D_3, D_4, D_5, D_6};
    }

    public static float[] getA() {
        return new float[]{A_1, A_2, A_3, A_4, A_5, A_6};
    }

    public static float[] getAlpha() {
        return new float[]{ALPHA_1, ALPHA_2, ALPHA_3, ALPHA_4, ALPHA_5, ALPHA_6};
    }

    /* atan2 without "jumps". */
    public float[] qProp(float[] qRef, float k) {
        float[] qNew = this.q;

        for (int i = 0; i < 6; i++) {
            float diff = qRef[i] - this.q[i];
            if (abs(diff) != 0) {
                qNew[i] = k * (diff);
            }
        }
        return qNew;
//        float q_mod;
//        q_mod = atan2(s, c) + 2*PI*nPis[solSel][index];
//        if (abs(q_mod + 2*PI - qrs[index]) < abs(q_mod - qrs[index])) {
//            q_mod = q_mod + 2*PI;
//            nPis[solSel][index] += 1;
//        } else {
//            if (abs(q_mod - 2*PI - qrs[index]) < abs(q_mod - qrs[index])) {
//                q_mod = q_mod - 2*PI;
//                nPis[solSel][index] -= 1;
//            }
//        }
//        return q_mod;

    }
}