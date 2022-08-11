package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.Obstacle;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public Utils(){}

    public double[][] Obs2List(List<Obstacle> obsList) {

        double[][] col = new double[obsList.size()][];
        for(int i = 0;i<obsList.size();i++){
            Obstacle o = obsList.get(i);
            double[] row = new double[3];
            row[0] = o.getYc();
            row[1] = o.getXc();
            row[2] = o.getR();
            col[i]=row;
        }
        return col;
    }
    public BufferedImage createImage(byte[][][] bytes){
        int sz = bytes.length-1;
        BufferedImage image = new BufferedImage(sz,sz,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        int red = 0;
        int green = 0;
        int blue = 0;
        for(int x=0;x<sz;x++){
            for(int y=0;y<sz;y++){
                    red=Byte.toUnsignedInt(bytes[x][y][0]);
                    green=Byte.toUnsignedInt(bytes[x][y][1]);
                    blue=Byte.toUnsignedInt(bytes[x][y][2]);
                //Construct color
                Color color= new Color(red,green,blue);
                g.setColor(color);//Set color
                g.fillRect(y,x,1,1);//Fill pixel
            }
        }
        return image;
    }

    public ArrayList<Image> makeImage(BufferedImage[] buffimg)  {
        ArrayList<Image> imgviews = new ArrayList<Image>();
        for(BufferedImage im: buffimg){
            imgviews.add(SwingFXUtils.toFXImage(im,null));
        }
        return  imgviews;
    }
}
