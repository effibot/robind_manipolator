package com.effibot.robind_manipolator.TCP;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class ClientServerInputReader extends Thread{
    private static Socket socket;
    private static Semaphore semaphore;
    private static HashMap toSend=null;
    private static ObjectOutputStream output;
    @Override
    public synchronized void run(){
            try {
                if(toSend==null||!(toSend instanceof HashMap)) {
                    System.out.println("Releasing Semaphore");
                    semaphore.release();
                }else {
                    semaphore.acquire();
                    System.out.println("Input acquiring Semaphore");
                    System.out.println("InputReader gets a permit.");

                    System.out.println("Sending message...");

                    OutputStream outsocket = socket.getOutputStream();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    output = new ObjectOutputStream(out);
                    output.writeObject(toSend);
                    byte[] msg = out.toByteArray();

                    output.flush();
                    output.close();
                    outsocket.write(msg);

                    System.out.println("Releasing Semaphore");
                    toSend = null;
                    semaphore.release();
                }
            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

    }
    public void setToSend(HashMap toSend) {
            this.toSend=toSend;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


}