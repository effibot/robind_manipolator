package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class TCPFacade {
    private static ClientServerOutputReader out =null;

    private static ClientServerInputReader in= null;
    private static Socket socket;
    private static Semaphore semaphore;
    private static TCPFacade instance = null;

    private static Utils utils = new Utils();


    public void startTCPComunication(String hostAddr, int port, int permits){
        try {
            socket = new Socket(hostAddr, port);
            semaphore = new Semaphore(permits,true);
            if(out==null){out = new ClientServerOutputReader();}

            if(in== null){in = new ClientServerInputReader();}
            in.setSocket(socket);
            in.setSemaphore(semaphore);
            in.start();
            out.setSocket(socket);
            out.setSemaphore(semaphore);
            out.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setJoin(){
        try {
            in.join();
            out.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public double[][] receiveMsg(){
        try {
            out.join();
            out.run();
            ArrayList<double[]> msg = out.getParsedMessage();
            return (double[][]) msg.toArray();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void releaseSendingSemaphore(){
        in.releaseSemaphore();
    }

    public static ClientServerOutputReader getOut() {
        return out;
    }

    public static ClientServerInputReader getIn() {
        return in;
    }

    public void stopTCPCommunication(){
        in.interrupt();
        if (in.currentThread().isInterrupted()){in = null;}
        out.interrupt();
        if(out.currentThread().isInterrupted()){out = null;}
    }
    public  static synchronized TCPFacade getInstance(){
        if(instance==null){
            instance = new TCPFacade();
        }
        return instance;
    }

    public void sendMsg(HashMap p) {
        in.setToSend(p);
        try {
            in.join();
            in.run();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}