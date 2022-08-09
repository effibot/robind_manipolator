package com.effibot.robind_manipolator.MATLAB;

public record ik(double[] qik) {
    public ik(double[] qik){
        this.qik = qik;
    }
}
