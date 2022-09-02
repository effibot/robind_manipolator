package com.effibot.robind_manipolator;

import com.effibot.robind_manipolator.Processing.Obstacle;
import com.squareup.gifencoder.FloydSteinbergDitherer;
import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {
private static final Random random = new Random();
private static final String GIF_PATH = "src/main/resources/com/effibot/robind_manipolator/img/test.gif";
private static GifEncoder genc;
private static final ImageOptions opt = new ImageOptions();
private static FileOutputStream out;
    public Utils(){
        opt.setDelay(100, TimeUnit.MILLISECONDS);
        opt.setDitherer(FloydSteinbergDitherer.INSTANCE);
    }

    public String getGifPath(){ return GIF_PATH; }
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
    public static Double[] irle(Double[] encStream){
        /**
         * Inverse Run Length Encoding
         */
        int seqLen = 0;
        for(int i = 0; i < encStream.length; i+=2){
            seqLen += encStream[i];
        }
        List<Double> decStream = new ArrayList<>(seqLen);
        seqLen = 0;
        for(int i = 0; i < encStream.length; i+=2){
            decStream.addAll(seqLen, Collections.nCopies(encStream[i].intValue(),encStream[i+1]));
            seqLen = decStream.size();
        }
        return decStream.toArray(new Double[0]);
    }
    public static Double[][] matrixReshape(Double[][] nums, int r, int c) {
        int totalElements = nums.length * nums[0].length;
        if (totalElements != r * c || totalElements % r != 0) {
            return nums;
        }
        final Double[][] result = new Double[r][c];
        int newR = 0;
        int newC = 0;
        for (Double[] num : nums) {
            for (Double aDouble : num) {
                result[newR][newC] = aDouble;
                newC++;
                if (newC == c) {
                    newC = 0;
                    newR++;
                }
            }
        }
        return result;
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
    public static int[] arrayDoubleToArrayInt(Double[] arr) {
        double[] array = ArrayUtils.toPrimitive(arr);
        if (array == null)
            return null;

        final int[] result = new int[array.length];
        final int n = array.length;

        for (int i = 0; i < n; i++) {
            result[i] = (int) array[i];
        }

        return result;
    }



    public static void stream2img(byte[] byteStream) {
        /*
         * The encoder must be set as follows:
         * GifEncoder genc = new GifEncoder(out,1024,1024,1);
         */

        try{
            if(out == null) {// checks for nullity on the output stream and the encoder
                // if the stream is closed open it
                Utils.setOut(new FileOutputStream(GIF_PATH));
                setGifEncoder(new GifEncoder(Utils.getOut(),1024,1024,1));
            }
            // decompress incoming stream and cast to int array
            int[] unzip = Utils.arrayDoubleToArrayInt(
                    Utils.irle(ArrayUtils.toObject((double[])Utils.decompress(byteStream))));
            assert unzip != null;
            // generate gif
            getGifEncoder().addImage(unzip, 1024, opt);
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeStreamEnc(){
        try {
            Utils.getOut().close();
            Utils.setOut(null);
            setGifEncoder(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static FileOutputStream getOut() {
        return out;
    }
    public static void setOut(FileOutputStream out) {
        Utils.out = out;
    }
    public static synchronized void setGifEncoder(GifEncoder genc){
        Utils.genc = genc;
    }
    public static synchronized GifEncoder getGifEncoder() throws IOException {
        if (Utils.genc == null)
            Utils.genc = new GifEncoder(Utils.getOut(), 1024, 1024, 1);
        return Utils.genc;
    }
//    TODO: remove when deploy
//    public static void main(String[] args){
//        Double[][] dec = new Double[][]{irle(new Double[]{3d,1d,3d,10d,3d,5d,3d,1d})};
//
//        Double[][] M = matrixReshape(dec, 12,1);
//        System.out.println(Arrays.toString(M));
//    }
}