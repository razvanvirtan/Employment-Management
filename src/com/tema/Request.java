package com.tema;

public class Request<K, V> implements Comparable{
    private K key;
    private V value1, value2;
    private Double score;

    public Request(K key, V value1, V value2, Double score) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.score = score;
    }

    public K getKey() {
        return key;
    }

    public V getValue1() {
        return value1;
    }

    public V getValue2() {
        return value2;
    }

    public Double getScore() {
        return score;
    }

    public String toString() {
        return "Key: " + key + " ; Value1: " + value1 + " ; Value2: " + value2 +
            " ; Score: " + score;
    }

    public String toHumanReadable() {
        return  key + " " + value1 + " " + value2 +
                " Score: " + String.format("%.1f", score);
    }

    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException();
        if (!(o instanceof Request))
            throw new ClassCastException("Tipurile de comparat sunt diferite!");

        Request<K, V> toCompare = (Request<K, V>) o;

        return -1 * score.compareTo(toCompare.score);
    }
}
