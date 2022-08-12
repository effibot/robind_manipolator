package com.effibot.robind_manipolator.matlab;

import java.awt.image.BufferedImage;

public record info(double[] gids, double[][] shapepos, BufferedImage bw, BufferedImage[] mapwk, BufferedImage graph) {
    public info(double[] gids, double[][] shapepos, BufferedImage bw, BufferedImage[] mapwk, BufferedImage graph){
        this.gids = gids;
        this.shapepos = shapepos;
        this.bw = bw;
        this.mapwk=mapwk;
        this.graph=graph;
    }

}
