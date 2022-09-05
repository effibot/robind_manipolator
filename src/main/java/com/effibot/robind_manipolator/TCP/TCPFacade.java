package com.effibot.robind_manipolator.TCP;


import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TCPFacade extends Thread {
    private Socket socket;
    private volatile boolean runFlag;

    private static TCPFacade instance = null;

    private static final String hostAddr = "localhost";
    private static final int port = 3030;

    private GameState gm = GameState.getInstance();
    private ReentrantLock lock1;
    private Condition empty;
    private Condition full;
    private Condition produced;
    private Condition consumed;
    private boolean stop = false;

    private ConcurrentLinkedQueue<HashMap<String, Object>> queueSend;
    private ReentrantLock lock2;
    private ConcurrentLinkedQueue<HashMap<String, Object>> queueReceive;


    private TCPFacade(){
    }
    public static synchronized TCPFacade getInstance(){
        if(instance==null){
            instance = new TCPFacade();
        }
        return instance;
    }


    @SuppressWarnings("unchecked")
    @Override
    public synchronized void run() {
        ObjectInputStream ois;

        try  {
            while (!isInterrupted()) {
                lock1.lock();

                while (queueSend.isEmpty()) {
                    System.out.println("TCP WAIT");
                    full.await();
                }

                lock1.unlock();
                Socket socket = new Socket(hostAddr, port);
                InputStream inSocketStream = socket.getInputStream();
                OutputStream outSocketStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outSocketStream);
                oos.writeObject(queueSend.poll());
                System.out.println("Message Sent");
                oos.reset();
                oos.writeInt(255);
                oos.reset();
                while (!gm.getStop()){
                    lock2.lock();
                    if (!queueReceive.isEmpty()) {
                        System.out.println("TCP CONSUME WAIT");
                        consumed.await();
                    }
                    lock2.unlock();

                    while (inSocketStream.available() > 0) {
                        ois = new ObjectInputStream(inSocketStream);
                        Object obj = ois.readObject();
                        if (obj != null) {
                            lock2.lock();
                            System.out.println("Receiving message");
                            queueReceive.offer( (HashMap<String, Object>) obj);
                            System.out.println("TCP ADD QUEUE");
                            produced.signalAll();

                            lock2.unlock();
                        }
                    }
            }
                oos.close();
                socket.close();


            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLock(ReentrantLock sendLock,
                        ReentrantLock recLock, Condition empty,
                        Condition full,
                        ConcurrentLinkedQueue<HashMap<String, Object>> queue,
                        ConcurrentLinkedQueue<HashMap<String, Object>> queueRec,
                        Condition produced, Condition consumed) {
        this.lock2 = recLock ;
        this.queueReceive = queueRec;
        this.lock1 = sendLock;
        this.empty = empty;
        this.full = full;
        this.queueSend = queue;
        this.produced = produced;
        this.consumed = consumed;
    }
}