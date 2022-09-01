package com.effibot.robind_manipolator.TCP;

import com.effibot.robind_manipolator.Utils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class TCPFacade implements Runnable {

    private  Socket socket;
    private static TCPFacade instance = null;

    private static  Utils utils;

    private static final int permits = 1;
    private static Semaphore semaphore ;

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
    @SuppressWarnings("unchecked")
    public void sendReceiveMsg() {
        ObjectInputStream ois = null;
        try (
                Socket socket = new Socket(hostAddr, port);
                OutputStream outSocketStream = socket.getOutputStream();
                InputStream inSocketStream = socket.getInputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outSocketStream);
        ) {

            System.out.println("Sending message...");
            oos.writeObject(toSend);
            System.out.println("Message sent.");
            oos.reset();
            oos.writeInt(255);
            System.out.println("Sending Terminator");
            oos.reset();


            while (true) {
                if (inSocketStream.available() > 0) {
                    ois = new ObjectInputStream(inSocketStream);
                    System.out.println("Receiving message...");
                    Object obj = ois.readObject();
                    if (obj != null) {
                        HashMap<String, Object> pkt = (HashMap<String, Object>) obj;
                        System.out.println("Message received.");
//                        GameState.getInstance().setPkt(pkt);
                        queue.put(pkt);
                        if ((double) pkt.get("FINISH") == 1.0) {
                            ois.close();
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
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




    public void setQueue(java.util.concurrent.BlockingQueue<java.util.HashMap<java.lang.String,java.lang.Object>> queue) {
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
    public void run() {
        if(toSend != null) {
            sendReceiveMsg();
        } else {
            System.out.println("Fill the message to send");
        }
    }

}