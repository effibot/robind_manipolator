package com.effibot.robind_manipolator.MATLAB;

public record path(double[][] q, double[][] dq, double[][] ddq) {
    public path(double[][] q, double[][] dq, double[][] ddq){
        this.q = q;
        this.dq = dq;
        this.ddq = ddq;
    }

}
