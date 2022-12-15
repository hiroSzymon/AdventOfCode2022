package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@SuppressWarnings("SameParameterValue")
public class Day9 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static final TreeSet<Pair<Integer, Integer>> visitedByTail = new TreeSet<>((o1, o2) -> {
        if (Objects.equals(o1.getValue(), o2.getValue())) {
            return o1.getKey().compareTo(o2.getKey());
        } else
            return o1.getValue().compareTo(o2.getValue());
    });

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        MutablePair<Integer, Integer> headPosition = MutablePair.of(0, 0);
        MutablePair<Integer, Integer> tailPosition = MutablePair.of(0, 0);
        visitedByTail.clear();
        visitedByTail.add(Pair.of(tailPosition));

        for (String s : input) {
            char direction = s.split(" ")[0].charAt(0);
            int value = Integer.parseInt(s.split(" ")[1].trim());

            visitedByTail.addAll(makeMove(headPosition, tailPosition, direction, value));
        }

        System.out.println("PART 1: visited by tail: " + visitedByTail.size());


    }

    private static void part2(ArrayList<String> input) {
        ArrayList<MutablePair<Integer, Integer>> linePositions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            linePositions.add(MutablePair.of(0, 0));
        }

        MutablePair<Integer, Integer> tailPosition = linePositions.get(9);

        visitedByTail.clear();
        visitedByTail.add(Pair.of(tailPosition));

        for (String s : input) {
            char direction = s.split(" ")[0].charAt(0);
            int value = Integer.parseInt(s.split(" ")[1].trim());

            for (int v = 0; v < value; v++) {

                for (int i = 0; i < linePositions.size() - 1; i++) {
                    Set<Pair<Integer, Integer>> tmpSet = makeMove(linePositions.get(i), linePositions.get(i + 1), direction, i > 0 ? 0 : 1);
                    if (i == linePositions.size() - 2) {
                        visitedByTail.addAll(tmpSet);
                    }

                }
            }
        }

        System.out.println("PART 2: visited by tail: " + visitedByTail.size());


    }

    private static Set<Pair<Integer, Integer>> makeMove(MutablePair<Integer, Integer> headPosition, MutablePair<Integer, Integer> tailPosition, char direction, int value) {
        HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
        switch (direction) {
            case 'U' -> headPosition.setRight(headPosition.getRight() + value);
            case 'D' -> headPosition.setRight(headPosition.getRight() - value);
            case 'L' -> headPosition.setLeft(headPosition.getLeft() - value);
            case 'R' -> headPosition.setLeft(headPosition.getLeft() + value);
        }

        int xDiff = headPosition.getLeft() - tailPosition.getLeft();
        int yDiff = headPosition.getRight() - tailPosition.getRight();

        if (Math.abs(xDiff) < 2 && Math.abs(yDiff) < 2)
            return Collections.emptySet();

        if (xDiff != 0 && yDiff != 0) {
            tailPosition.setLeft(tailPosition.getLeft() + (xDiff > 0 ? 1 : -1));
            tailPosition.setRight(tailPosition.getRight() + (yDiff > 0 ? 1 : -1));
            visited.add(Pair.of(tailPosition));

            xDiff = modifyDiff(xDiff);
            yDiff = modifyDiff(yDiff);

        }

        while (Math.abs(xDiff) > 1) {
            tailPosition.setLeft(tailPosition.getLeft() + (xDiff > 0 ? 1 : -1));
            visited.add(Pair.of(tailPosition));
            xDiff = modifyDiff(xDiff);
        }

        while (Math.abs(yDiff) > 1) {
            tailPosition.setRight(tailPosition.getRight() + (yDiff > 0 ? 1 : -1));
            visited.add(Pair.of(tailPosition));
            yDiff = modifyDiff(yDiff);
        }

        return visited;
    }

    private static int modifyDiff(int diff) {
        if (diff > 1) {
            diff -= 1;
        } else if (diff < -1) {
            diff += 1;
        }

        return diff;
    }


}
