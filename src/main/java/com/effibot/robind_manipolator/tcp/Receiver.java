package com.effibot.robind_manipolator.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
public class Receiver implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class.getName());
    private final Semaphore[] sem;
    private final  Socket socket;
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;

    public Receiver(Semaphore[] sem, Socket socket, BlockingQueue<LinkedHashMap<String, Object>> queue) {
        this.sem = sem;
        this.socket = socket;
        this.queue = queue;
    }

    @Override @SuppressWarnings("unchecked")
    public void run() {
        try{
            sem[1].acquire();
            LOGGER.debug("Receiver Lock.\tSem[0] = {}, Sem[1] = {}\n",sem[0].availablePermits(),sem[1].availablePermits());

            while (!Thread.currentThread().isInterrupted()) {
                InputStream is = socket.getInputStream();
                if(is.available() >0 ) {
                    ObjectInputStream ois = new ObjectInputStream(is);
                    Object obj = ois.readObject();
                    if (obj != null) {
                        LinkedHashMap<String, Object> pkt = (LinkedHashMap<String, Object>) obj;
                        queue.put(pkt);
                        if ((Double) pkt.get("FINISH") == 1d) {
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            LOGGER.warn("Receiver: Interrupted by TCP.EVENT.RESET");
            LinkedHashMap<String, Object> pkt = new LinkedHashMap<>();
            pkt.put("FINISH",-99d);
            try {
                queue.clear();
                queue.put(pkt);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }finally {
            sem[0].release();
            LOGGER.info("Receiver Unlock.\tSem[0] = {}, Sem[1] = {}\n",sem[0].availablePermits(),sem[1].availablePermits());
        }

    }
}