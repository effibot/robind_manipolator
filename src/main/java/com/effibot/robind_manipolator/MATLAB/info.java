package com.effibot.robind_manipolator.MATLAB;

public record info(double[] gids, double[][] shapepos) {
    public info(double[] gids, double[][] shapepos){
        this.gids = gids;
        this.shapepos = shapepos;
    }

}
