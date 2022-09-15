package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.processing.Obstacle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class.getName());
    private Utils(){
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
            LOGGER.warn("Compression Error",e);
            return new byte[0];
        }
    }
    public static Object decompress(byte[] stream){
        try(ByteArrayInputStream bais = new ByteArrayInputStream(stream);
            GZIPInputStream in = new GZIPInputStream(bais);
            ObjectInputStream ois = new ObjectInputStream(in)){
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.warn("Decompression Error",e);
            return null;
        }
    }

}