package com.effibot.robind_manipolator.Processing;

import com.effibot.robind_manipolator.MATLAB.sysout;

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();

}
