package com.effibot.robind_manipolator.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class TCPFacade implements PropertyChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCPFacade.class.getName());
    private Socket clientSocket;
    private static TCPFacade instance = null;
    private final Thread[] t = new Thread[2];
    private final Semaphore[] sem = {new Semaphore(1), new Semaphore(0)};

    private static final String HOST_ADDR = "localhost";
    private static  int PORT = 3030;
    private static final  BlockingQueue<LinkedHashMap<String, Object>> queue = new LinkedBlockingQueue<>(1);

    private HashMap<String,Object> toSend;
    private TCPFacade(){

    }
    public static synchronized TCPFacade getInstance(){
        if(instance==null){
            instance = new TCPFacade();
        }
        return instance;
    }

    public BlockingQueue<LinkedHashMap<String, Object>> getQueue() {
        return queue;
    }

    public Map<String, Object> getToSend() {
        return toSend;
    }

    public void setToSend(Map<String, Object> toSend) {
        this.toSend = (HashMap<String, Object>) toSend;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName){
            case "SEND" ->{
                try {// Checks if connection are still up
                    HashMap<String, Object> pkt = (HashMap<String, Object>) evt.getNewValue();


                    if (clientSocket == null) {
                        clientSocket = new Socket(HOST_ADDR, PORT);
                        clientSocket.setReuseAddress(true);
                    }
                    // Make new Packet to send
                    Sender sender = new Sender(sem, clientSocket);
                    sender.setToSend(pkt);
                    // send msg to Matlab Server
                    t[0] = new Thread(sender);
                    t[0].start();
//                    if(pkt.containsKey("SK")){
//                        PORT = (int) pkt.get("SK");
//                        clientSocket=new Socket(HOST_ADDR, PORT);
//                    }
                } catch (IOException e) {
                    LOGGER.warn("SENDER EXCEPTION",e);
                    Thread.currentThread().interrupt();
                }
            }
            case "RECEIVE" ->{
                // receiver from Matlab Server
                Receiver receiver = new Receiver(sem, clientSocket, queue);
                t[1] = new Thread(receiver);
                t[1].start();
            }
            default -> LOGGER.warn("Unknown Property Name");
        }
    }

    public void resetSocket() {
        try(InputStream is= clientSocket.getInputStream();
            OutputStream ois = clientSocket.getOutputStream();
        ){
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
        }
        catch (IOException e) {
            LOGGER.warn("Resetting Socket");
        }finally {
            clientSocket=null;
        }
    }
}