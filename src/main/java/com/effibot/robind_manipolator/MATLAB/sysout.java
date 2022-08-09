package com.effibot.robind_manipolator.MATLAB;

public record sysout(double[] q, double[] dq, double[] ddq, double[] e) {
    public sysout(double[] q, double[] dq, double[] ddq, double[] e){
        this.q = q;
        this.dq=dq;
        this.ddq = ddq;
        this.e = e;
    }
}
