package com.effibot.robind_manipolator.TCP;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ClientServerInputReader extends Thread{
    private Socket socket;
    private  Semaphore semaphore;
    private  OutputStream outSocket;

    private  HashMap toSend=null;
    private ObjectOutputStream output;


    @Override
    public synchronized void run(){
            try {
                if(toSend != null) {
                    semaphore.drainPermits();
                    System.out.println("Input acquiring Semaphore");
                    System.out.println("Sending message...");
                    output.writeObject(toSend);

                    System.out.println("Message sent.");
                    toSend = null;
                    outSocket.flush();
                    output.flush();
                    System.out.println("Sending Terminator");
                    output.writeInt(255);
                    outSocket.flush();
                    output.flush();
                }

            }
            catch (IOException  e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("Releasing Semaphore.");
                semaphore.release();
            }

    }
    public void setToSend(HashMap toSend) {
            this.toSend=toSend;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.outSocket = this.socket.getOutputStream();
            this.output = new ObjectOutputStream(outSocket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


}