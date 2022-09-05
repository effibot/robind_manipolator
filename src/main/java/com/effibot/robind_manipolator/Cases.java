package com.effibot.robind_manipolator;

import java.util.HashMap;

public  class Cases {
    public final HashMap<String,Object> cases= new HashMap<>();

    public Cases(int c){
        cases.put("CASE",c);
    }

    public int getCases() {
        synchronized (cases) {
            return (int) cases.get("CASE");
        }
    }

    public void setCases(int c) {
        synchronized (cases) {
            cases.put("CASE",c);
        }
    }
}