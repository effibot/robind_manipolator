package com.effibot.robind_manipolator.TCP;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ClientServerInputReader{
    private Socket socket;
    private  Semaphore semaphore;
    private  OutputStream outSocket;

    private  HashMap toSend=null;
    private ObjectOutputStream output;


    public void run(){


    }
    public void setToSend(HashMap toSend) {
            this.toSend=toSend;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


}