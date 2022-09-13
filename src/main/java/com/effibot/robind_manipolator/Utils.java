package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.Obstacle;
import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {

    public Utils(){
        /*
        * Public constructor
        * */
    }
    public static double[][] obs2List(List<Obstacle> obsList) {

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
    public static byte[] compress(Object obj){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(baos);
        ObjectOutputStream oos = new ObjectOutputStream(out)){
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object decompress(byte[] stream){
        try(ByteArrayInputStream bais = new ByteArrayInputStream(stream);
            GZIPInputStream in = new GZIPInputStream(bais);
            ObjectInputStream ois = new ObjectInputStream(in)){
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}