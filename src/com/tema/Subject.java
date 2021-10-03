package com.tema;

import java.util.LinkedList;

public abstract class Subject {
    LinkedList<Observer> observers;

    Subject(){
        observers = new LinkedList<>();
    }

    public void addObserver(User user) {
        observers.add(user);
    }

    public void removeObserver(User c) {
        observers.remove(c);
    }

    public abstract void notifyAllObservers(Job job);
}
