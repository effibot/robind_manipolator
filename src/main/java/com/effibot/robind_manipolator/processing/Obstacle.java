package com.effibot.robind_manipolator.processing;

import processing.core.PApplet;

public class Obstacle{
    private float xc;
    private float yc;
    private float zc;
    private float r;
    private float h;
    private int id;



    private ProcessingBase p;
    public Obstacle(ProcessingBase p, float xc, float yc, float zc, float r, float h, int id){
        this.xc = xc;
        this.yc = yc;
        this.zc = zc;
        this.r = r;
        this.h = h;
        this.id = id;
        this.p =  p;
    }

    @Override
    public String toString(){
        return("""
        Obstacle BC: {%f,%f,%f}
        + Obstacle ID: {%d}
        + Obstacle [r, h]: {%f,%f}
        """.formatted(this.xc, this.yc,this.zc, this.id, this.r, this.h));
    }
    public void drawObstacle2D(){
        p.pushMatrix();
        p.fill(150,200,100);
        p.rect(this.xc/2+10, this.yc/2+10,this.r/2,this.r/2);
        p.popMatrix();
    }
    public float getXc() {
        return xc;
    }

    public void setXc(float xc) {
        this.xc = xc;
    }
    public float getYc() {
        return yc;
    }
    public void setYc(float yc) {
        this.yc = yc;
    }
    public float getZc() {
        return zc;
    }
    public void setZc(float zc) {
        this.zc = zc;
    }
    public float getR() {
        return r;
    }
    public void setR(float r) {
        this.r = r;
    }
    public float getH() {
        return h;
    }
    public void setH(float h) {
        this.h = h;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public PApplet getP() {
        return p;
    }
    public void setP(ProcessingBase p) {
        this.p = p;
    }
    public void adaptTo3D(){
        setXc(this.getXc()+p.getPadding());
        setYc(this.getYc()+p.getPadding());
    }
}