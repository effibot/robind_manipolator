package com.effibot.robind_manipolator.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Sender implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class.getName());
    private  final  Semaphore[] sem;
    private final Socket socket;

    private HashMap<String, Object> toSend;

    public Sender(Semaphore[] s, Socket socket) {
        this.sem = s;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            sem[0].acquire();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            if (!toSend.isEmpty()) {
                LOGGER.debug("Sender Lock.\tSem[0] = {}, Sem[1] = {}\n", sem[0].availablePermits(), sem[1].availablePermits());
                oos.writeObject(toSend);
                oos.flush();
                oos.writeInt(255);
                oos.flush();
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.warn("Sender: Interrupted by TCP.EVENT.RESET", e);
            Thread.currentThread().interrupt();
        }
        finally {

            sem[1].release();
            LOGGER.info("Sender Unlock.\tSem[0] = {}, Sem[1] = {}\n", sem[0].availablePermits(), sem[1].availablePermits());
        }

    }

    public Semaphore[] getSem() {
        return sem;
    }

    public Socket getSocket() {
        return socket;
    }

    public Map<String, Object> getToSend() {
        return toSend;
    }

    public void setToSend(Map<String, Object> toSend) {
        this.toSend =(HashMap<String, Object>) toSend;
    }
    public void setToSend(String key, Object value){
        this.toSend.put(key,value);
    }
}