package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import org.apache.commons.io.*;
public class ClientServerOutputReader extends Thread {
    private static Socket socket;
    private static Semaphore semaphore;
    public  static  ArrayList<HashMap> arrayHash = new ArrayList<>();
    private static ClientServerOutputReader instance = null;
    private static final Utils utils = new Utils();
    private int stop = 0;
    @Override
    public synchronized void run() {

        try {


            while (socket.isConnected()) {
                if(socket.getInputStream().available()>0) {
                    System.out.println("OutputReader receiving message...");

                    InputStream ski = socket.getInputStream();
                    ObjectInputStream dis = new ObjectInputStream(ski);
                    Object obj = dis.readObject();
                    if (obj != null) {
                        HashMap pck = (HashMap) obj;
                        System.out.println("Message read...");

                        if((double) pck.get("FINISH")==1) {


                            return;
                        }
                        arrayHash.add(pck);

                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();


        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        ClientServerOutputReader.socket = socket;
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    public static void setSemaphore(Semaphore semaphore) {
        ClientServerOutputReader.semaphore = semaphore;
    }

    public ArrayList<HashMap> getParsedMessage() {
        return arrayHash;
    }
}