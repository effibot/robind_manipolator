package com.effibot.robind_manipolator.TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Receiver implements Runnable{
    private final Semaphore[] sem;
    private final Socket socket;
    private final BlockingQueue<HashMap<String, Object>> queue;

    public Receiver(Semaphore[] sem, Socket socket, BlockingQueue<HashMap<String, Object>> queue) {
        this.sem = sem;
        this.socket = socket;
        this.queue = queue;
    }

    @Override @SuppressWarnings("unchecked")
    public void run() {
        ObjectInputStream ois;
        try {
            sem[1].acquire();
            InputStream is = socket.getInputStream();
            while (true) {
                ois = new ObjectInputStream(is);
                if (is.available() > 0) {
                    Object obj = ois.readObject();
                    if (obj != null) {
                        System.out.println("Receiving");
                        HashMap<String, Object> pkt = (HashMap<String, Object>) obj;
                        queue.put(pkt);
                        if ((Double)pkt.get("FINISH") == 1d) {
                            System.out.println("finished");
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            sem[0].release();
            System.out.println("rilascio finally");
        }

    }
}
