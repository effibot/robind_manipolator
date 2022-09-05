package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class TCPFacade implements PropertyChangeListener {

    private Socket clientSocket;
    private static TCPFacade instance = null;

    private static  Utils utils;

    private static final int permits = 1;
    private static final Semaphore[] sem = {new Semaphore(1), new Semaphore(0)};

    private static final String hostAddr = "localhost";
    private static final int port = 3030;
    private BlockingQueue<HashMap<String, Object>> queue;

    private HashMap<String,Object> toSend;
    private TCPFacade(){
            utils = new Utils();
    }
    public static synchronized TCPFacade getInstance(){
        if(instance==null){
            instance = new TCPFacade();
        }
        return instance;
    }



    public void setQueue(BlockingQueue<HashMap<String,Object>> queue) {
        this.queue = queue;
    }
    public BlockingQueue<HashMap<String, Object>> getQueue() {
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
                    if (clientSocket == null) clientSocket = new Socket(hostAddr, port);
                    // Make new Packet to send
                    HashMap<String, Object> pkt = (HashMap<String, Object>) evt.getNewValue();
                    Sender sender = new Sender(sem, clientSocket);
                    sender.setToSend(pkt);
                    // send msg to Matlab Server
                    new Thread(sender).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "RECEIVE" ->{
                // receiver from Matlab Server
                Receiver receiver = new Receiver(sem, clientSocket, queue);
                new Thread(receiver).start();
            }
            default -> System.out.println("Unknown Property Name");
        }
    }
}