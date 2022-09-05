import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import processing.core.PShape;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import static java.lang.Math.*;
import static processing.core.PConstants.PI;
import shapes3d.*;
import shapes3d.contour.*;
import shapes3d.org.apache.commons.math.*;
import shapes3d.org.apache.commons.math.geometry.*;
import shapes3d.path.*;
import shapes3d.utils.*;
public class Robot {
    // Theta
    private float[] q = new float[]{0, -PI / 2, PI / 2, 0, 0, 0};
    //private float[] q = new float[]{-2.4004,2.1881,-3.1451,-23.9823,1.2456,26.1715};
    // d
    private final float d1 = 33.0f;
    private final float d2 = 0.0f;
    private final float d3 = 0.0f;
    private final float d4 = 51.0f;
    private final float d5 = 0.0f;
    private final float d6 = 12.0f;
    private final float[] d = new float[]{d1, d2, d3, d4, d5, d6};
    // Alpha
    private final float alpha1 = -PI / 2;
    private final float alpha2 = 0.0f;
    private final float alpha3 = PI / 2;
    private final float alpha4 = -PI / 2;
    private final float alpha5 = PI / 2;
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
    // scalefactor for shapes
    private float scaleFactor;
    
    
    // Observers
    public Robot() {
        this.shapeList.add(loadLink(0));
        for (int i = 0; i < 6; i++) {
            // load link_i
            this.shapeList.add(loadLink(i + 1));
            // Assign DH table
            Vector<Float> dhRow = new Vector<>(Arrays.asList(q[i], d[i], alpha[i], a[i]));
            dhTable.add(dhRow);
        }
    }
    Ellipsoid shape;
    public void dh(float theta, float d, float alpha, float a) {
        rotateZ(theta);
        translate(0, 0, d);
        rotateX(alpha);
        translate(a, 0, 0);
    }
    private PShape loadLink(int id) {
        File file = sketchFile("models/r" + id + "c.obj");
        String objPath = file.getAbsolutePath();
        PShape shape = loadShape(objPath);
        if (id == 0) {
            //  scaleFactor = 30.0f;
            scaleFactor = 9f;
        } else {
            scaleFactor = 1;
        }
        shape.scale(scaleFactor);
        return shape;
    }
    public void drawLink() {
        this.getShapeList().get(0).setFill(color(102,51,0));
        //! Offset dalla mappa 9.5        
        pushMatrix();
        rotateX(PI / 2);
        //rotateY(PI/2);
        shape(this.getShapeList().get(0));
        popMatrix();                          
        
        // dh 01
        Vector<Float> r01 = this.getDhTable().get(0);
        this.dh(r01.get(0), r01.get(1), r01.get(2), r01.get(3));
        this.getShapeList().get(1).setFill(color(100, 200, 0));
        pushMatrix();
        //  zeroFrame = new Reference(p3d,new PVector(0,0,0));
        //  zeroFrame.show(true);
        //show(0,dx,0,true);
        rotateY( -PI / 2);
        rotateZ(PI);
        translate(0, -25, 0);
        shape(this.getShapeList().get(1));
        popMatrix();
        // dh 12
        Vector<Float> r12 = this.getDhTable().get(1);
        this.dh(r12.get(0), r12.get(1), r12.get(2), r12.get(3));
        this.getShapeList().get(2).setFill(color(40, 2, 100));
        pushMatrix();
        translate( -50,0,0);
        rotateY(PI / 2);
        shape(this.getShapeList().get(2));
        popMatrix();
        // dh 23
        Vector<Float> r23 = this.getDhTable().get(2);
        this.dh(r23.get(0), r23.get(1), r23.get(2), r23.get(3));
        this.getShapeList().get(3).setFill(color(100, 200, 0));
        pushMatrix();
        //  zeroFrame = new Reference(this,new PVector(0,0,0));
        //  zeroFrame.show(true);
        rotateZ(PI / 2);
        rotateX( -PI);
        shape(this.getShapeList().get(3));
        popMatrix();
        // dh 34
        Vector<Float> r34 = this.getDhTable().get(3);
        this.dh(r34.get(0), r34.get(1), r34.get(2), r34.get(3));
        this.getShapeList().get(4).setFill(color(40, 2, 100));
        pushMatrix();
        //  zeroFrame = new Reference(p3d,new PVector(0,0,0));
        //  zeroFrame.show(true);
        
        translate(0, 51, 0);
        rotateX( -PI / 2);
        rotateZ(PI / 2);
        shape(this.getShapeList().get(4));
        popMatrix();
        // dh 45
        Vector<Float> r45 = this.getDhTable().get(4);
        this.dh(r45.get(0), r45.get(1), r45.get(2), r45.get(3));
        this.getShapeList().get(5).setFill(color(100, 200, 0));
        pushMatrix();
        //  zeroFrame = new Reference(p3d,new PVector(0,0,0));
        //  zeroFrame.show(true);
        translate(0, 0, -1);
        rotateZ(PI / 2);
        shape(this.getShapeList().get(5));
        popMatrix();
        stroke(0);
        strokeWeight(1);
        //sphere(abs(a2 - d4));
        sphere(d6);
        noStroke();stroke(0);
        strokeWeight(1);
        //sphere(abs(a2 - d4));
        sphere(d6);
        noStroke();
        // dh 56
        Vector<Float> r56 = this.getDhTable().get(5);
        this.dh(r56.get(0), r56.get(1), r56.get(2), r56.get(3));
        
        this.getShapeList().get(6).setFill(color(40, 2, 100));
        pushMatrix();
        //  zeroFrame = new Reference(p3d,new PVector(0,0,0));
        //  zeroFrame.show(true);
        //show(0,0,0,true);
        translate(0, 0, -13);
        shape(this.getShapeList().get(6));
        popMatrix();
    }
    
    public Vector<Float> getDHrow(int rIndex) {
        return this.dhTable.get(rIndex);
    }
    public Vector<Vector<Float>> getDhTable() {
        return this.dhTable;
    }
    public void setDhTable(Vector<Vector<Float>> newTable) { this.dhTable = newTable;}
    public void setDhTable(float[] joints) {
        int id = 0;
        for (float q : joints) {
            this.setJoint(id, q);
            id += 1;
        }
    }
    public void setJoint(int rIndex, float qNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
        //tempRow.set(0, tempRow.get(0)+qNew);
        tempRow.set(0,qNew);
        this.dhTable.set(rIndex, tempRow);
    }
    public void setDistance(int rIndex, int cIndex, float dNew) {
        Vector<Float> tempRow = getDHrow(rIndex);
        tempRow.set(cIndex, tempRow.get(1) + dNew);
        this.dhTable.set(rIndex, tempRow);
    }
    
    public PShape getShape(int id) {
        return this.shapeList.get(id);
    }
    public ArrayList<PShape> getShapeList() {
        return this.shapeList;
    }
    
    
    //private float xdes, ydes, zdes = 0;
    
    public float[] inverseKinematics(float xdes, float ydes, float zdes,float roll, float pitch, float yaw, int elbow) {
        //println(xdes);
        //println(ydes);
        //println(zdes);
        /* inversa di po
        sizione */
        // posizione del polso
        float xh = xdes - d6 * cos(roll) * sin(pitch);
        float yh = (ydes - d6 * sin(roll) * sin(pitch));
        float zh = zdes - d6 * cos(pitch);
        // setups for next calculations
        float B2 = -elbow * sqrt(pow(xh,2) + pow(yh,2));
        float B1 = d1 - zh;
        
        // sin(q[3])
        float ds3 = 2 * a2 * d4;
        float ns3 = pow(B1,2) + pow(yh,2) + pow(xh,2) - pow(a2,2) - pow(d4,2);
        float s3 = (ns3) / ds3;
        // cos(q[3])
        float c3 = elbow * sqrt(1 - pow(s3,2));
        // q[3]
        float q3 = atan2((s3),(c3));
        // setups for next calculations
        //float A12 =( a2+d4*s3);
        //float A11 = -d4*c3;
        //float det = pow(A11,2)-pow(A12,2);
        //// cos(q[2])
        ////float c2 = -(B2*A12-A11*B1)/det;
        //// sin(q[2])
        
        //float c2  = (d2*elbow*sqrt(pow(xh,2)+pow(yh,2))-d1*d4*c3+d4*zh*c3+d4*s3*B2)/
        //(pow(a2,2)+2*s3*a2*a4+pow(d4,2));
        //float s2 = (d1*a2-a2*zh-d4*zh*s3+d1*d4*s3+d4*c3*B2)/( pow(a2,2)+2*s3*a2*a4+pow(d4,2));
        //// q[2]
        //float q2 = atan2((s2),(c2));
        float A11 = -d4 * c3;
        float A12 = a2 + d4 * s3;
        float A21 = -A12;
        float A22 = A11;
        float det = A11 * A22 - A12 * A21;
        float c2 = -(A12 * B2 - A22 * B1) / det;
        float s2 = (A11 * B2 - A21 * B1) / det;
        float q2 = atan2(s2,c2);
        //// another setup
        //float k = a2*c2+d4*sin(q2+q3);
        float k = a2 * c2 + d4 * (s2 * c3 + c2 * s3);
        // cos(q[1])
        float c1 = xh / k;
        // sin(q[1])
        float s1 = yh / k;
        // q[1]
        float q1 = atan2((s1),(c1));
        /* inversa di orientamento */
        // Setup for next calculations
        RealMatrix zRoll = MatrixUtils.createRealMatrix(rotateZm(roll));
        RealMatrix yPitch = MatrixUtils.createRealMatrix(rotateYm(pitch));
        RealMatrix zYaw = MatrixUtils.createRealMatrix(rotateZm(yaw));
        //RealMatrix xYaw = MatrixUtils.createRealMatrix(rotateXm(yaw));
        RealMatrix Rdes = zRoll.multiply(yPitch).multiply(zYaw);
        Vector<Vector<Float>> dh03 = new Vector<Vector<Float>>();
        dh03.add(getDhTable().get(0));
        dh03.add(getDhTable().get(1));
        dh03.add(getDhTable().get(2));
        double[][] dhValue = dhValue(dh03);
        RealMatrix Q03 = MatrixUtils.createRealMatrix(dhValue);
        RealMatrix R03T = Q03.getSubMatrix(0,2,0,2).transpose();
        RealMatrix R = R03T.multiply(Rdes);
        //printR(R);
        // q[5]
        float c5 = (float)R.getEntry(2,2);
        float s5 = elbow*sqrt(1-pow(c5,2));
        float q5 = atan2(s5,c5);
        // q[4]
        float c4 =  (float)R.getEntry(0,2)/s5;
        float s4 =  (float)R.getEntry(1,2)/s5;
        float q4 = atan2(s4,c4);
        // q[6]
        float c6 =  (float)-R.getEntry(2,0)/s5;
        float s6 =  (float)R.getEntry(2,1)/s5;
        float q6 = atan2(s6,c6);
        //println(new float[]{q1,q2,q3,q4,q5,q6});
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
        private void printR(RealMatrix R){
        System.out.println(String.format(
        "[%f,%f,%f]\n[%f,%f,%f]\n[%f,%f,%f]",
        R.getEntry(0,0),R.getEntry(0,1),R.getEntry(0,2),R.getEntry(1,0),R.getEntry(1,1),R.getEntry(1,2),R.getEntry(2,0),R.getEntry(2,1),R.getEntry(2,2))
        );
    }
        public double [][] translateM(float x, float y, float z){
        return new double[][]{{x},{y},{z}};
    }
        public double[][] rotor(String axis, float theta, float d){
        float theta_r = (theta); //<>//
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
        for(int j = 0; j < 3; j++){
        av[i][j] = R[i][j];
    }
    }
        for(int k = 0; k < 3; k++){
        av[k][3] = T[k][0];
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
        public void printEF(){
        
    }
    }
        