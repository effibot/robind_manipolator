package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
public class ClientServerOutputReader extends Thread{
    private static   Socket socket;
    private static Semaphore semaphore;
    private static  ArrayList<double[]> arrayDoubbleList ;
    private static ClientServerOutputReader instance = null;
    private static final Utils utils = new Utils();

    @Override
    public  void run(){
        try {
            System.out.println("Output acquiring Semaphore");
            semaphore.drainPermits();
            System.out.println("OutputReader receiving message...");
            ObjectInputStream dis = new ObjectInputStream(socket.getInputStream());
            Object obj = dis.readObject();
            while (obj !=null) {
                HashMap pck = (HashMap) obj;
            }
            System.out.println("No message to read");
            System.out.println("Releasing Semaphore");
            semaphore.release();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    public ArrayList<double[]> getParsedMessage() {
        return arrayDoubbleList;
    }
}