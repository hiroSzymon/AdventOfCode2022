package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Day12 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static Vertex<Integer> startPos = null;
    private static Vertex<Integer> endPos = null;
    private static Graph graph;
    private static Vertex<Integer>[][] vertices;

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        List<char[]> chars = input.stream().filter(e -> !e.isEmpty()).map(String::toCharArray).toList();
        vertices = generateGraphVertices(chars);

        graph = Graph.emptyGraph().fillWithVertices(vertices);
        startPos=graph.getStartPos();
        endPos=graph.getEndPos();

        graph.generateShortestPathsFromSourceVertex(startPos);

        char[][] grph = generateMap(vertices);

        int steps = endPos.getShortestPath().size();

        System.out.println("PART 1: steps: " + steps);
        //Arrays.stream(grph).forEach(e -> System.out.println(Arrays.toString(e)));

    }

    private static void part2(ArrayList<String> input) {
        List<char[]> chars = input.stream().filter(e -> !e.isEmpty()).map(String::toCharArray).toList();
        int minSteps = Integer.MAX_VALUE;
        int verticesLength = vertices.length;
        int verticesRowLength = vertices[0].length;
        for (int i=0; i<verticesLength; i++){
            for(int j=0; j<verticesRowLength; j++) {
                if (vertices[i][j].getHeight() == 'a' || vertices[i][j].getHeight() == 'S') {
                    vertices = generateGraphVertices(chars);
                    graph = Graph.emptyGraph().fillWithVertices(vertices);
                    startPos=vertices[i][j];
                    endPos=graph.getEndPos();
                    graph.generateShortestPathsFromSourceVertexSkippingA(startPos);
                    if(minSteps>endPos.getShortestPath().size() && endPos.getShortestPath().size()>0)
                        minSteps=endPos.getShortestPath().size();
                }
            }
        }
        System.out.println("PART 2: steps: " + minSteps);

    }

    private static char[][] generateMap(Vertex<Integer>[][] map) {
        char[][] grph = new char[map.length][map[0].length];
        for (char[] value : grph) Arrays.fill(value, '.');

        for (int i = 0; i < endPos.getShortestPath().size() - 1; i++) {
            char c = '.';
            if (endPos.getShortestPath().get(i + 1).getPosition().getKey() > endPos.getShortestPath().get(i).getPosition().getKey())
                c = 'v';
            if (endPos.getShortestPath().get(i + 1).getPosition().getKey() < endPos.getShortestPath().get(i).getPosition().getKey())
                c = '^';
            if (endPos.getShortestPath().get(i + 1).getPosition().getValue() > endPos.getShortestPath().get(i).getPosition().getValue())
                c = '>';
            if (endPos.getShortestPath().get(i + 1).getPosition().getValue() < endPos.getShortestPath().get(i).getPosition().getValue())
                c = '<';

            grph[endPos.getShortestPath().get(i).getPosition().getKey()][endPos.getShortestPath().get(i).getPosition().getValue()] = c;
        }
        return grph;
    }


    public static Vertex<Integer>[][] generateGraphVertices(List<char[]> chars) {
        Vertex<Integer>[][] vertices = new Vertex[chars.size()][chars.get(0).length];

        for (int i = 0; i < chars.size(); i++) {
            for (int j = 0; j < chars.get(i).length; j++) {
                vertices[i][j] = new Vertex<>(i, j, (int) chars.get(i)[j]);
            }
        }
        return vertices;
    }



}
