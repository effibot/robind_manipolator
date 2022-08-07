package com.effibot.robind_manipolator;

import hypermedia.net.UDP;

import static processing.core.PApplet.subset;

public class UdpModule {
    private static volatile UdpModule instance=null;
    private String addr;
    private int portRX;
    private int portTX;

    private static UDP sk;
    private static float proc;
    private UdpModule(){}

    public void openConnection(){
        this.sk = new UDP(this, this.portTX, this.addr);
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPortRX() {
        return portRX;
    }

    public void setPortRX(int portRX) {
        this.portRX = portRX;
    }

    public int getPortTX() {
        return portTX;
    }

    public void setPortTX(int portTX) {
        this.portTX = portTX;
    }


    public void sendData(float[] toSend){
        int size=toSend.length;
        this.proc = toSend[0];
        String str="[";
        for (int i=0; i<size;i++){
            if (i==size-1){
                str=str+toSend[i]+"]";
            }else{
                str = str+toSend[i]+",";
            }
        }
        this.sk.send(str, this.addr,this.portTX);

    }

    void receive( byte[] data ) {
        data = subset(data, 0, data.length);
        String message = new String(data);
        switch (Math.round(this.proc)) {
            case 1:
                break;
            case 2:
                break;

        }
    }


        public static UdpModule getInstance(){
            if(instance==null){
                synchronized(UdpModule.class){
                    if(instance==null){
                        instance= new UdpModule();
                    }
                }
            }
            return instance;
        }


    private float[] parse(String str){
        float[] r=new float[1];
        if(str!=null){
            String nobrackets = str.replaceAll("[\\p{Ps}\\p{Pe}]", "");
            String[] strValues= nobrackets.split(",");
            r = new float[strValues.length];
            for(int i=0;i<strValues.length ;i++){
                r[i]=Float.parseFloat(strValues[i]);
            }
        }
        return r;
    }

}
