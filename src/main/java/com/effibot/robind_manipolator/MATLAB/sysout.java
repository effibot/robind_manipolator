package com.effibot.robind_manipolator.MATLAB;

import java.awt.image.BufferedImage;

public record sysout(double[][] q, double[][] dq, double[][] ddq, double[][] e, BufferedImage[] mappid) {
    public sysout(double[][] q, double[][] dq, double[][] ddq, double[][] e, BufferedImage[] mappid){
        this.q = q;
        this.dq=dq;
        this.ddq = ddq;
        this.e = e;
        this.mappid=mappid;
    }
}
