package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.Obstacle;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    public ArrayList<Image> makeImage(String filepath)  {
        File path = new File(filepath);
        File[] allFiles = path.listFiles();
        Arrays.sort(allFiles, new Comparator<File>() {
            public int compare(File f1, File f2) {
                try {
                    String s1 = String.valueOf(f1.getName().lastIndexOf("."));
                    String s2 = String.valueOf(f2.getName().lastIndexOf("."));

                    if (s1.equals("mapid")){
                        s1= "1000000";
                    }
                    else if(s2.equals("mapid")){
                        s2="1000000";
                    }

                    int i1 = Integer.parseInt(s1);
                    int i2 = Integer.parseInt(s2);
                    return i1 - i2;
                } catch(NumberFormatException e) {
                    throw new AssertionError(e);
                }
            }
        });
        Image[] allImages = new Image[allFiles.length];
        ArrayList<Image> imgviews = new ArrayList<Image>();
        for(int i =0;i< allImages.length;i++){
            allImages[i]= new Image("file:"+allFiles[i]);
//            ImageView tmp = new ImageView(allImages[i]);
//            imgviews.add(allImages[i]);
        }
        imgviews.addAll(List.of(allImages));
        return  imgviews;
    }
}
