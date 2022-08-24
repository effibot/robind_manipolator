package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.Obstacle;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
private static final Random random = new Random();

    public Utils(){/* Default Constructor */}

    public double[][] obs2List(List<Obstacle> obsList) {

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

    public List<Image> makeImage(BufferedImage[] buffimg)  {
        ArrayList<Image> imgviews = new ArrayList<>();
        for(BufferedImage im: buffimg){
            imgviews.add(SwingFXUtils.toFXImage(im,null));
        }
        return  imgviews;
    }
        public List<String> randomSequence(int n){
        ArrayList<String> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++)
        {
            list.add(String.valueOf(random.nextInt(1000)));
        }
        return list;
    }

    public ArrayList<double[]> toArrayDoubleList(Double[][] darr){
        ArrayList<double[]> arr = new ArrayList<>();
        int i = 0;
        for(Double[] d:darr){
           arr.add(ArrayUtils.toPrimitive(d));
        }
        return arr;
    }
}