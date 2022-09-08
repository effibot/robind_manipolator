package com.effibot.robind_manipolator.tcp;

public class Lock {
    private boolean lock = false;

    public synchronized boolean isLock() {
        return lock;
    }

    public boolean lock(){
        if(!isLock()) lock = false;
        return lock;
    }

    public boolean unlock(){
        if(isLock()) lock = false;
        return lock;
    }
}