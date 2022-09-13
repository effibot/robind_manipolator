package com.effibot.robind_manipolator.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Receiver implements Runnable{
    private final Semaphore[] sem;
    private final Socket socket;
    private final BlockingQueue<LinkedHashMap<String, Object>> queue;

    public Receiver(Semaphore[] sem, Socket socket, BlockingQueue<LinkedHashMap<String, Object>> queue) {
        this.sem = sem;
        this.socket = socket;
        this.queue = queue;
    }

    @Override @SuppressWarnings("unchecked")
    public void run() {
        ObjectInputStream ois;
        try {
            sem[1].acquire();
            System.out.format("Receiver Lock.\tSem[0] = %d, Sem[1] = %d\n",sem[0].availablePermits(),sem[1].availablePermits());
            InputStream is = socket.getInputStream();
            while (true) {
                if (is.available() > 0) {
                    ois = new ObjectInputStream(is);
                    Object obj = ois.readObject();
                    if (obj != null) {
                        LinkedHashMap<String, Object> pkt = (LinkedHashMap<String, Object>) obj;
                        queue.put(pkt);
                        if ((Double)pkt.get("FINISH") == 1d) {
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            sem[0].release();
            System.out.format("Receiver Unlock.\tSem[0] = %d, Sem[1] = %d\n",sem[0].availablePermits(),sem[1].availablePermits());
        }

    }
}