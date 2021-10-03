package com.tema;

public class Constraint<V> {
    private V upperBound;
    private V lowerBound;

    Constraint(V upperBound, V lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public V getUpperBound() {
        return upperBound;
    }

    public V getLowerBound() {
        return lowerBound;
    }
}
