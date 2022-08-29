package com.effibot.robind_manipolator.TCP;

public class GameState {
    private static GameState instance;
    private  double[] greenId;
    private  double[][] obslist;
    private double[] shapepos;
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

    private GameState( ){

    }

    public double[] getGreenId() {
        return greenId;
    }

    public void setGreenId(double[] greenId) {
        this.greenId = greenId;
    }

    public double[][] getObslist() {
        return obslist;
    }

    public void setObslist(double[][] obslist) {
        this.obslist = obslist;
    }

    public double[] getShapepos() {
        return shapepos;
    }

    public void setShapepos(double[] shapepos) {
        this.shapepos = shapepos;
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


    public synchronized static GameState getInstance(){
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
}