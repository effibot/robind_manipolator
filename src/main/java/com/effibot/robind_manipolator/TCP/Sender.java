package com.effibot.robind_manipolator.TCP;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Sender implements Runnable{
    private final Semaphore[] sem;
    private final Socket socket;

    private HashMap<String, Object> toSend;

    public Sender(Semaphore[] s, Socket socket) {
        this.sem = s;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            assert(toSend != null);
            sem[0].acquire();
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(toSend);
            oos.writeInt(255);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            sem[1].release();
        }
    }

    public Semaphore[] getSem() {
        return sem;
    }

    public Socket getSocket() {
        return socket;
    }

    public HashMap<String, Object> getToSend() {
        return toSend;
    }

    public void setToSend(HashMap<String, Object> toSend) {
        this.toSend = toSend;
    }
    public void setToSend(String key, Object value){
        this.toSend.put(key,value);
    }
}
