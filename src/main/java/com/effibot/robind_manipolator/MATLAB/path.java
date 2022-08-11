package com.effibot.robind_manipolator.MATLAB;

import java.awt.image.BufferedImage;

public record path(double[][] q, double[][] dq, double[][] ddq, BufferedImage[] mapsimimg) {
    public path(double[][] q, double[][] dq, double[][] ddq, BufferedImage[] mapsimimg){
        this.q = q;
        this.dq = dq;
        this.ddq = ddq;
        this.mapsimimg=mapsimimg;
    }

}
