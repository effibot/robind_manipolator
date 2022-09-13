package com.effibot.robind_manipolator.tcp;

public class Lock {
    private boolean lock = false;

    public synchronized boolean isLocked() {
        return lock;
    }

    public void lock(){
        if(!isLocked()) lock = true;
    }

    public void unlock(){
        if(isLocked()) lock = false;
    }
}