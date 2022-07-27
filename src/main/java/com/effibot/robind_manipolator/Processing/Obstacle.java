package com.effibot.robind_manipolator.Processing;

import processing.core.PApplet;

public class Obstacle{
    private float xc;
    private float yc;
    private float zc;
    private float r;
    private float h;
    private int id;
    private PApplet p;
    public Obstacle(PApplet p, float xc, float yc, float zc, float r, float h, int id){
        this.xc = xc;
        this.yc = yc;
        this.zc = zc;
        this.r = r;
        this.h = h;
        this.id = id;
        this.p =  p;
        //System.out.println(this);
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
        p.circle(this.xc/2+10, this.yc/2+10,2*this.r);
        p.popMatrix();
    }
}