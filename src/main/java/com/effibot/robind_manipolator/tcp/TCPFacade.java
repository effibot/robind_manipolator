package com.effibot.robind_manipolator.tcp;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class TCPFacade implements PropertyChangeListener {

    private Socket clientSocket;
    private static TCPFacade instance = null;
    private final Thread[] t = new Thread[2];
    private Semaphore[] sem = {new Semaphore(1), new Semaphore(0)};

    private static final String HOST_ADDR = "localhost";
    private static final int PORT = 3030;
    private static final BlockingQueue<LinkedHashMap<String, Object>> queue = new LinkedBlockingQueue<>(1);

    private HashMap<String,Object> toSend;
    private TCPFacade(){

    }
    public static synchronized TCPFacade getInstance(){
        if(instance==null){
            instance = new TCPFacade();
        }
        return instance;
    }



//    public void setQueue(BlockingQueue<LinkedHashMap<String,Object>> queue) {
//        this.queue = queue;
//    }
    public BlockingQueue<LinkedHashMap<String, Object>> getQueue() {
        return queue;
    }

    public HashMap<String, Object> getToSend() {
        return toSend;
    }

    public void setToSend(HashMap<String, Object> toSend) {
        this.toSend = toSend;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName){
            case "SEND" ->{
                try {// Checks if connection are still up
                    if (clientSocket == null) clientSocket = new Socket(HOST_ADDR, PORT);
                    // Make new Packet to send
                    HashMap<String, Object> pkt = (HashMap<String, Object>) evt.getNewValue();
                    Sender sender = new Sender(sem, clientSocket);
                    sender.setToSend(pkt);
                    // send msg to Matlab Server
                    t[0] = new Thread(sender);
                    t[0].start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "RECEIVE" ->{
                // receiver from Matlab Server
                Receiver receiver = new Receiver(sem, clientSocket, queue);
                t[1] = new Thread(receiver);
                t[1].start();
            }
            case "RESET" ->{
                try {
                    if(clientSocket!=null && clientSocket.isConnected()) {
                        clientSocket.close();
                    }
                    if(t[0] != null)
                        t[0].interrupt();
                    if(t[1] != null )
                        t[1].interrupt();
                    sem = new Semaphore[]{new Semaphore(1), new Semaphore(0)};
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            default -> System.out.println("Unknown Property Name");
        }
    }
}