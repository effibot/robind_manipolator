package com.effibot.robind_manipolator.MATLAB;

public record geomprops(double area, double perim, double shape, double orient) {
    public geomprops(double area, double perim, double shape, double orient){
        this.area = area;
        this.perim = perim;
        this.shape = shape;
        this.orient = orient;
    }
}
