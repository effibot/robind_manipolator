package com.effibot.robind_manipolator.tcp;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;

public class GameState {
    private static GameState instance;
    private  double[] greenId;
    private  double[][] obsList;
    private double[] shapePos;
    private  double[][] gq;
    private  double[][] gdq;
    private  double[][] gddq;
    private  double[][] sq;
    private  double[][] sdq;
    private  double[][] sddq;
    private double[][] e;
    private double selectedShape;
    private double roll;
    private double pitch;
    private double yaw;
    private double xdes;
    private double ydes;
    private double zdes;
    private byte[] byteStream;
    private HashMap<String, Object> pkt;
    private final PropertyChangeSupport changes;
    private double startid;
    private String method;
    private byte[] animation = null;
    private byte[] rawImg = null;
    private boolean shapeAvailable = false;
    private String pathLabel=null;
    private boolean isFinished = false;
    private double[] qik;
    private double[] errorNewton;
    private double area;
    private double perim;
    private double orient;
    private String forma;

    private GameState( ){
        changes = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
    public void notifyPropertyChange(String propertyName, Object oldValue, Object newValue){
        /*
         * Just a wrapper for the fire property change method.
         */
        changes.firePropertyChange(propertyName, oldValue, newValue);
    }
    public double[] getGreenId() {
        return greenId;
    }

    public void setGreenId(double[] greenId) {
        this.greenId = greenId;
        changes.firePropertyChange("ID",null, greenId);
    }
    public void setAnimation(byte[] writableImage){
        this.animation = writableImage;
        changes.firePropertyChange("ANIMATION",false,true);
    }

    public byte[] getAnimation(){return this.animation;}
    public double[][] getObsList() {
        return obsList;
    }

    public void setObsList(double[][] obsList) {
        this.obsList = obsList;
    }

    public double[] getShapePos() {
        return shapePos;
    }

    public void setShapePos(double[] shapePos) {
        this.shapePos = shapePos;
    }

    public double[][] getGq() {
        return gq;
    }

    public void setGq(double[][] gq) {
        this.gq = gq;
    }

    public double[][] getGdq() {
        return gdq;
    }

    public void setGdq(double[][] gdq) {
        this.gdq = gdq;
    }

    public double[][] getGddq() {
        return gddq;
    }

    public void setGddq(double[][] gddq) {
        this.gddq = gddq;
    }

    public double[][] getSq() {
        return sq;
    }

    public void setSq(double[][] sq) {
        this.sq = sq;
    }

    public double[][] getSdq() {
        return sdq;
    }

    public void setSdq(double[][] sdq) {
        this.sdq = sdq;
    }

    public double[][] getSddq() {
        return sddq;
    }

    public void setSddq(double[][] sddq) {
        this.sddq = sddq;
    }

    public double[][] getE() {
        return e;
    }

    public void setE(double[][] e) {
        this.e = e;
    }


    public static synchronized  GameState getInstance(){
        if (instance == null){
            instance = new GameState();
        }
        return instance;
    }

    public double getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(double selectedShape) {
        this.selectedShape = selectedShape;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getXdes() {
        return xdes;
    }

    public void setXdes(double xdes) {
        this.xdes = xdes;
    }

    public double getYdes() {
        return ydes;
    }

    public void setYdes(double ydes) {
        this.ydes = ydes;
    }

    public double getZdes() {
        return zdes;
    }

    public void setZdes(double zdes) {
        this.zdes = zdes;
    }
    public void setPkt(HashMap<String,Object> pkt) { this.pkt = pkt; }
    public HashMap<String, Object> getPkt() { return pkt; }
    public byte[] getByteStream() {
        return byteStream;
    }

    public void setByteStream(byte[] byteStream) {
        this.byteStream = byteStream;
    }

    public void setStartId(double startid) {
        this.startid = startid;
    }

    public double getStartId(){
        return startid;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod(){
        return method;
    }


    public void setRaw(byte[] imgEnanched) {
        this.rawImg = imgEnanched;
        changes.firePropertyChange("BW",false,true);
    }

    public byte[] getRaw(){return this.rawImg;}

    public void setShapeAvailable(boolean b) {
        this.shapeAvailable = b;
        changes.firePropertyChange("ERROR",false,true);
    }

    public boolean getShapeAvailable() {
        return this.shapeAvailable;
    }

    public void setPathLabel(double[] ids) {
        StringBuilder sb = new StringBuilder();
        for(double id : ids){
            sb.append("[").append(String.valueOf(id).split("\\.")[0]).append("]").append(" -> ");
            if((id ==  ids[ids.length-1])){
                sb.append("Fine");
            }
        }
        changes.firePropertyChange("PATHLABEL",pathLabel,sb.toString());
        pathLabel = sb.toString();

    }

    public String getPathLabel() {
        return pathLabel;
    }

    public void setQIK(double[] qik) {
        this.qik = qik;
    }

    public double[] getQIK(){ return qik;}

    public void setEnewton(double[] eNewton) {
        this.errorNewton = eNewton;
    }
    
    public double[] getEnewton(){return this.errorNewton;}

    public void setArea(double area) {
        this.area = area;
    }
    public double getArea(){return area;}
    public void setPerim(double perim) {
        this.perim = perim;
    }
    public double getPerim(){return perim;}
    public void setOrient(double orient) {
        this.orient = orient;
    }
    public double getOrient(){return orient;}

    public void setForma(String forma) {
        this.forma = forma;
    }
    public String getForma(){return forma;}
}