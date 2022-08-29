package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class TCPFacade {
    private  ClientServerOutputReader out;

    private  ClientServerInputReader in;
    private  Socket socket;
    private static TCPFacade instance = null;

    private static  Utils utils;

    private static final int permits = 1;
    private static Semaphore semaphore ;

    private static final String hostAddr = "localhost";
    private static final int port = 3030;

    private TCPFacade(){

            semaphore =new Semaphore(permits,true);
            out = new ClientServerOutputReader();
            in=  new ClientServerInputReader();
            utils = new Utils();
            in.setSemaphore(semaphore);
            out.setSemaphore(semaphore);
            in.start();
            out.start();

    }


    public ClientServerOutputReader getOut() {
        return this.out;
    }

    public ClientServerInputReader getIn() {
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

    public ArrayList<HashMap> sendMsg(HashMap p) {
        try {
            Thread.sleep(5000);
            socket = new Socket(hostAddr, port);
            socket.setTcpNoDelay(true);
            in.setSocket(socket);
            out.setSocket(socket);
            in.setToSend(p);
            in.run();
            in.join();
            out.setStop(0);
            out.run();
            out.join();
            socket.close();
            return  out.getParsedMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
//    public ArrayList<HashMap> receiveMsg(){
//        try {
//            semaphore.acquire();
//            System.out.println("Output acquiring Semaphore");
//            ArrayList<HashMap> msg = null;
//            out.run();
//            out.join();
//            socket.close();
//            msg = out.getParsedMessage();
//            return msg;
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }finally {
//            semaphore.release();
//
//        }
//        return null;
//    }

    public  void waitResponse(){
        try {
            this.out.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public  void setOut(ClientServerOutputReader out) {
        this.out = out;
    }

    public  void setIn(ClientServerInputReader in) {
        this.in = in;
    }


    public void flushBuffer() {
        out.resetBuffer();
    }
}