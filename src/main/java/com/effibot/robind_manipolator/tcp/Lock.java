package com.effibot.robind_manipolator.tcp;

public class Lock {
    private boolean mutex = false;

    public synchronized boolean isLocked() {
        return mutex;
    }

    public void lock(){
        if(!isLocked()) mutex = true;
    }

    public void unlock(){
        if(isLocked()) mutex = false;
    }
}