package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class TCPFacade {
    private static ClientServerOutputReader out;

    private static ClientServerInputReader in;
    private static Socket socket;
    private static TCPFacade instance = null;

    private static  Utils utils;

    private static final int permits = 1;
    private static Semaphore semaphore ;

    private static final String hostAddr = "localhost";
    private static final int port = 3030;

    private TCPFacade(){
        try {
            socket = new Socket(hostAddr, port);
            semaphore =new Semaphore(permits,true);
            out = new ClientServerOutputReader();
            in=  new ClientServerInputReader();
            utils = new Utils();
            in.setSemaphore(semaphore);
            in.setSocket(socket);
            out.setSocket(socket);
            out.setSemaphore(semaphore);
            in.start();
            out.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            in.run();
            in.join();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
    public ArrayList<HashMap> receiveMsg(){
        try {
            semaphore.acquire();
            System.out.println("Output acquiring Semaphore");
            int stop = 0;
            ArrayList<HashMap> msg = null;
            out.run();
            out.join();
            msg = out.getParsedMessage();

            return msg;
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setOut(ClientServerOutputReader out) {
        TCPFacade.out = out;
    }

    public static void setIn(ClientServerInputReader in) {
        TCPFacade.in = in;
    }

}