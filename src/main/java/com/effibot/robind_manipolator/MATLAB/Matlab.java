package com.effibot.robind_manipolator.MATLAB;

import MatlabUtility.MatlabUtility;
import com.effibot.robind_manipolator.Processing.Obstacle;
import com.mathworks.toolbox.javabuilder.*;

import java.util.List;

public class Matlab extends MatlabUtility {
    private static MatlabUtility matlabUtility = null;

    private Matlab() throws MWException {
        matlabUtility = new MatlabUtility();
    }

    public info mapgeneration(double[][] obs, double[] dim) {
        MWArray obsIn = null;
        MWArray dimIn = null;
        MWNumericArray gidOut = null;
        MWNumericArray shapeposOut = null;
        Object[] results = null;
        try {
            obsIn = new MWNumericArray(obs, MWClassID.DOUBLE);
            dimIn = new MWNumericArray(dim, MWClassID.DOUBLE);
            results = matlabUtility.mapGeneration(2, obsIn, dimIn);
            if (results[0] instanceof MWNumericArray) {
                gidOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                shapeposOut = (MWNumericArray) results[1];
            }
            System.out.println(gidOut);
            System.out.println(shapeposOut);
            info res = new info( gidOut.getDoubleData(),shapeposOut.getDoubleData());
            return  res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{obsIn, dimIn});
            MWArray.disposeArray(results);
        }
        return null;
    }

    public path pathgeneration(double startId,double[] shapepos, String method){
        MWArray startIdIn = null;
        MWArray shapeposIn = null;
        MWArray methodIn = null;
        MWNumericArray pOut = null;
        MWNumericArray dpOut = null;
        MWNumericArray ddpOut = null;
        Object[] results = null;
        try {
            startIdIn = new MWNumericArray(startId, MWClassID.DOUBLE);
            shapeposIn = new MWNumericArray(shapepos, MWClassID.DOUBLE);
            methodIn = new MWCharArray(method);
            results = matlabUtility.path_generator(3, startIdIn, shapeposIn, methodIn);
            if (results[0] instanceof MWNumericArray) {
                pOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                dpOut = (MWNumericArray) results[1];
            }
            if (results[2] instanceof MWNumericArray) {
                ddpOut = (MWNumericArray) results[2];
            }
            System.out.println(pOut);
            System.out.println(dpOut);
            System.out.println(ddpOut);
            path res = new path(pOut.getDoubleData(),dpOut.getDoubleData(),ddpOut.getDoubleData());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{startIdIn,shapeposIn,methodIn});
            MWArray.disposeArray(results);
        }
        return null;
    }

    public sysout runsimulation(double M, double alpha){
        MWArray MIn = null;
        MWArray alphaIn = null;
        MWNumericArray qrOut = null;
        MWNumericArray dqrOut = null;
        MWNumericArray ddqrOut = null;
        MWNumericArray eOut = null;
        Object[] results = null;
        try {
            MIn = new MWNumericArray(M, MWClassID.DOUBLE);
            alphaIn = new MWNumericArray(alpha, MWClassID.DOUBLE);
            results = matlabUtility.runsimulation(4, MIn, alphaIn);
            if (results[0] instanceof MWNumericArray) {
                qrOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                dqrOut = (MWNumericArray) results[1];
            }
            if (results[2] instanceof MWNumericArray) {
                ddqrOut = (MWNumericArray) results[2];
            }
            if (results[3] instanceof MWNumericArray) {
                eOut = (MWNumericArray) results[3];
            }
            System.out.println(qrOut);
            System.out.println(dqrOut);
            System.out.println(ddqrOut);
            System.out.println(eOut);
            sysout res = new sysout(qrOut.getDoubleData(), dqrOut.getDoubleData(), ddqrOut.getDoubleData(), eOut.getDoubleData());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{MIn,alphaIn});
            MWArray.disposeArray(results);
        }
        return  null;
    }

    public geomprops vision(String filename){
        MWArray filenameIn = null;
        MWNumericArray objAreaOut = null;
        MWNumericArray objPerimOut = null;
        MWNumericArray objShapeOut = null;
        MWNumericArray angOut = null;
        Object[] results = null;
        try {
            filenameIn = new MWCharArray(filename);
            results = matlabUtility.visione(4, filenameIn);
            if (results[0] instanceof MWNumericArray) {
                objAreaOut = (MWNumericArray) results[0];
            }
            if (results[1] instanceof MWNumericArray) {
                objPerimOut = (MWNumericArray) results[1];
            }
            if (results[2] instanceof MWNumericArray) {
                objShapeOut = (MWNumericArray) results[2];
            }
            if (results[3] instanceof MWNumericArray) {
                angOut = (MWNumericArray) results[3];
            }
            System.out.println(objAreaOut);
            System.out.println(objPerimOut);
            System.out.println(objShapeOut);
            System.out.println(angOut);
            geomprops res = new geomprops(objAreaOut.getDouble(),objPerimOut.getDouble(),objShapeOut.getDouble(), angOut.getDouble());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{filenameIn});
            MWArray.disposeArray(results);
        }
    return  null;
    }

    public ik inverse_kinematics(double xdes, double ydes,double zdes,double roll,double pitch,double yaw){
        MWArray x = null;
        MWArray y = null;
        MWArray z = null;
        MWArray r = null;
        MWArray p = null;
        MWArray yy = null;
        MWNumericArray qikOut = null;
        Object[] results = null;
        try {
            x = new MWNumericArray(xdes, MWClassID.DOUBLE);
            y = new MWNumericArray(ydes, MWClassID.DOUBLE);
            z = new MWNumericArray(zdes, MWClassID.DOUBLE);
            r = new MWNumericArray(roll, MWClassID.DOUBLE);
            p = new MWNumericArray(pitch, MWClassID.DOUBLE);
            y = new MWNumericArray(yaw, MWClassID.DOUBLE);

            results = matlabUtility.newtongrad(1,x,y,z,r,p,yy);
            if (results[0] instanceof MWNumericArray) {
                qikOut = (MWNumericArray) results[0];
            }
            System.out.println(qikOut);
            ik res = new ik(qikOut.getDoubleData());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Dispose of native resources
            disposeResources(new MWArray[]{x,y,z,r,p,yy});
            MWArray.disposeArray(results);
        }
        return null;
    }

    public static void disposeResources(MWArray[] mw){
        int sz = mw.length;
        for(int i=0;i<sz;i++) {
            MWArray.disposeArray(mw[i]);
        }
    }

    public void disposeMatlab(){
        matlabUtility.dispose();
    }

    public double[][] Obs2List(List<Obstacle> obsList) {

        double[][] col = new double[obsList.size()][];
        for(int i = 0;i<obsList.size();i++){
            Obstacle o = obsList.get(i);
            double[] row = new double[3];
            row[0] = o.getXc();
            row[1] = o.getYc();
            row[2] = o.getR();
            col[i]=row;
        }
        return col;
    }

    public  static  synchronized Matlab getInstance() throws MWException {
        if (matlabUtility==null){
            matlabUtility=new Matlab();
        }
        return (Matlab) matlabUtility;
    }
}
