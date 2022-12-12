package com.hiroszymon.aoc2022;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private HashSet<Vertex<Integer>> graphSet = new HashSet<>();
    private Vertex<Integer> startPos;
    private Vertex<Integer> endPos;
    private Vertex<Integer>[][] vertices;


    private Graph() {

    }

    public static Graph emptyGraph() {
        return new Graph();
    }

    public Graph fillWithVertices(Vertex<Integer>[][] vertices) {
        Graph graph = new Graph();
        graph.vertices = vertices;
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices[i].length; j++) {
                graph.graphSet.add(graph.generateVertexWithNeighbours(vertices, i, j));
            }
        }
        return graph;

    }

    private Vertex<Integer> generateVertexWithNeighbours(Vertex<Integer>[][] vertices, int y, int x) {
        Vertex<Integer> v = vertices[y][x];
        int leftDist, rightDist, upDist, downDist;

        char tmpCurrent = (char) v.getHeight().intValue();
        if (v.getHeight() == 'S') {
            tmpCurrent = 'a';
        } else if (v.getHeight() == 'E') {
            tmpCurrent = 'z';
            endPos = v;
        }

        int tmpNeigh;
        if (x > 0) {
            tmpNeigh = vertices[y][x - 1].getHeight();
            if (tmpNeigh == 'E')
                tmpNeigh = 'z';

            leftDist = -(tmpCurrent - tmpNeigh);
            if (leftDist < 2)
                v.addNeighbour(vertices[y][x - 1], leftDist + 1);
        }

        if (x < vertices[0].length - 1) {
            tmpNeigh = vertices[y][x + 1].getHeight();
            if (tmpNeigh == 'E')
                tmpNeigh = 'z';
            rightDist = -(tmpCurrent - tmpNeigh);
            if (rightDist < 2)
                v.addNeighbour(vertices[y][x + 1], rightDist + 1);

        }

        if (y > 0) {
            tmpNeigh = vertices[y - 1][x].getHeight();
            if (tmpNeigh == 'E')
                tmpNeigh = 'z';
            upDist = -(tmpCurrent - tmpNeigh);
            if (upDist < 2)
                v.addNeighbour(vertices[y - 1][x], upDist + 1);

        }
        if (y < vertices.length - 1) {
            tmpNeigh = vertices[y + 1][x].getHeight();
            if (tmpNeigh == 'E')
                tmpNeigh = 'z';
            downDist = -(tmpCurrent - tmpNeigh);
            if (downDist < 2)
                v.addNeighbour(vertices[y + 1][x], downDist + 1);

        }

        return v;
    }

    public void generateShortestPathsFromSourceVertex(Vertex<Integer> source) {
        source.setDistanceFromStart(0);

        Set<Vertex<Integer>> settledNodes = new HashSet<>();
        Set<Vertex<Integer>> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Vertex<Integer> currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Vertex<Integer>, Integer> adjacencyPair :
                    currentNode.getNeighboursAndDistance().entrySet()) {
                Vertex<Integer> adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    public void generateShortestPathsFromSourceVertexSkippingA(Vertex<Integer> source) {
        source.setDistanceFromStart(0);

        Set<Vertex<Integer>> settledNodes = new HashSet<>();
        Set<Vertex<Integer>> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Vertex<Integer> currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Vertex<Integer>, Integer> adjacencyPair :
                    currentNode.getNeighboursAndDistance().entrySet()) {
                if(adjacencyPair.getKey().getHeight()=='a'){
                    settledNodes.add(adjacencyPair.getKey());
                    continue;
                }
                Vertex<Integer> adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private Vertex<Integer> getLowestDistanceNode(Set<Vertex<Integer>> unsettledNodes) {
        Vertex<Integer> lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Vertex<Integer> node : unsettledNodes) {
            int nodeDistance = node.getDistanceFromStart();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Vertex<Integer> evaluationNode,
                                          Integer edgeWeigh, Vertex<Integer> sourceNode) {
        Integer sourceDistance = sourceNode.getDistanceFromStart();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistanceFromStart()) {
            evaluationNode.setDistanceFromStart(sourceDistance + edgeWeigh);
            LinkedList<Vertex<Integer>> getShortestPath = new LinkedList<>(sourceNode.getShortestPath());
            getShortestPath.add(sourceNode);
            evaluationNode.setShortestPath(getShortestPath);
        }
    }

    public Vertex<Integer> getStartPos() {
        return startPos == null ? findStartPos() : startPos;
    }

    public Graph setStartPos(Vertex<Integer> startPos) {
        this.startPos = startPos;
        return this;
    }

    public Vertex<Integer> getEndPos() {
        return endPos;
    }

    public Graph setEndPos(Vertex<Integer> endPos) {
        this.endPos = endPos;
        return this;
    }

    private Vertex<Integer> findStartPos() {
        for (Vertex<Integer>[] v : vertices)
            for (Vertex<Integer> u : v) {
                if (u.getHeight() == 'S')
                    return u;
            }
        return vertices[0][0];
    }

    public void clearVerticesPaths() {
        for (Vertex<Integer>[] v : vertices)
            for (Vertex<Integer> u : v) {
                u.setShortestPath(Collections.emptyList());
                u.setDistanceFromStart(Integer.MAX_VALUE);
            }
    }

    public void removeLessOrEqualNeighbours() {
        for (Vertex<Integer>[] v : vertices)
            for (Vertex<Integer> u : v) {
                List<Map.Entry<Vertex<Integer>, Integer>> tmpList = u.getNeighboursAndDistance().entrySet().stream().filter(e-> !e.getKey().getHeight().equals(u.getHeight())).toList();
                u.getNeighboursAndDistance().clear();
                tmpList.forEach((e)-> u.getNeighboursAndDistance().put(e.getKey(), e.getValue()));

            }
    }
}
