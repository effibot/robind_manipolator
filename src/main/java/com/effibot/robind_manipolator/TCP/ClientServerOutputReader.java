package com.effibot.robind_manipolator.TCP;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ClientServerOutputReader extends Thread {
    private InputStream ski;
    private ObjectInputStream dis;
    private  Socket socket;
    private  Semaphore semaphore;
    public  ArrayList<HashMap> arrayHash = new ArrayList<>();
    private static int stop = 0;


    @Override
    public synchronized void run() {
        System.out.println("Thread Reading is running...");
        try {
            if(this.socket!=null) {

            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();

        }
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public  Semaphore getSemaphore() {
        return semaphore;
    }

    public  void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public ArrayList<HashMap> getParsedMessage() {
        return arrayHash;
    }

    public void setStop(int toRecv) {
        this.stop = toRecv;
    }

    public void resetBuffer() {
        arrayHash = new ArrayList<>();
    }
}