package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {

    private final Pair<Integer, Integer> position;
    private final T height;
    private HashMap<Vertex<T>, Integer> neighboursAndDistance;
    private int distanceFromStart = Integer.MAX_VALUE;
    private List<Vertex<T>> shortestPath;

    public Vertex(int y, int x, T height) {
        this.height = height;
        this.position = Pair.of(y, x);
        neighboursAndDistance = new HashMap<>();
        shortestPath = new LinkedList<>();
    }

    public void addNeighbour(Vertex<T> neighbour, int distance) {
        neighboursAndDistance.put(neighbour, distance);
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public HashMap<Vertex<T>, Integer> getNeighboursAndDistance() {
        return neighboursAndDistance;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public List<Vertex<T>> getShortestPath() {
        return shortestPath;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public void setShortestPath(List<Vertex<T>> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public T getHeight() {
        return height;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(position, vertex.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
