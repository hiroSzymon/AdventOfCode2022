package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeSet;

@SuppressWarnings({"SuspiciousNameCombination", "SameParameterValue"})
public class Day14 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static int maxY;
    private static final TreeSet<Pair<Integer, Integer>> blockedPoints = getBlockedPoints(input);

    private static int grainCount = 0;


    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> ignoredInput) {
        maxY = blockedPoints.stream().mapToInt(Pair::getValue).max().orElse(0);
        System.out.println("PART 1: fallen grains: "+ countGrains(true));
    }

    private static void part2(ArrayList<String> ignoredInput) {
        maxY = blockedPoints.stream().mapToInt(Pair::getValue).max().orElse(0) + 2;
        System.out.println("PART 2: fallen grains: "+countGrains(false));
    }

    private static TreeSet<Pair<Integer, Integer>> getBlockedPoints(ArrayList<String> input) {
        TreeSet<Pair<Integer, Integer>> blockedPoints = new TreeSet<>();
        for (String s : input) {
            String[] lineEnds = s.split("->");
            for (int i = 0; i < lineEnds.length - 1; i++) {
                String from = lineEnds[i].trim();
                String to = lineEnds[i + 1].trim();
                int fromX = Integer.parseInt(from.split(",")[0]);
                int fromY = Integer.parseInt(from.split(",")[1]);
                int toX = Integer.parseInt(to.split(",")[0]);
                int toY = Integer.parseInt(to.split(",")[1]);
                for (int x = Math.min(fromX, toX); x <= Math.max(fromX, toX); x++) {
                    for (int y = Math.min(fromY, toY); y <= Math.max(fromY, toY); y++) {
                        blockedPoints.add(Pair.of(x, y));
                    }
                }
            }
        }
        return blockedPoints;
    }

    private static int countGrains(boolean isPart1) {
        Pair<Integer, Integer> grain = Pair.of(500, 0);
        while (nextValidPosition(grain, Day14.blockedPoints, isPart1).isPresent()) {
            grainCount++;
            grain = Pair.of(500, 0);
        }
        return grainCount;
    }

    private static Optional<Pair<Integer, Integer>> nextValidPosition(Pair<Integer, Integer> grain,
                                                                      TreeSet<Pair<Integer, Integer>> blockedPoints,
                                                                      boolean isPart1) {

        if (blockedPoints.contains(Pair.of(500, 0)))
            return Optional.empty();

        if (isPart1) {
            if (grain.getValue() > maxY)
                return Optional.empty();
        } else {
            if (grain.getValue() + 1 >= maxY) {
                blockedPoints.add(grain);
                return Optional.of(grain);
            }
        }

        if (!blockedPoints.contains(Pair.of(grain.getKey(), grain.getValue() + 1))) {
            return nextValidPosition(Pair.of(grain.getKey(), grain.getValue() + 1), blockedPoints, isPart1);
        } else if (!blockedPoints.contains(Pair.of(grain.getKey() - 1, grain.getValue() + 1))) {
            return nextValidPosition(Pair.of(grain.getKey() - 1, grain.getValue() + 1), blockedPoints, isPart1);
        } else if (!blockedPoints.contains(Pair.of(grain.getKey() + 1, grain.getValue() + 1))) {
            return nextValidPosition(Pair.of(grain.getKey() + 1, grain.getValue() + 1), blockedPoints, isPart1);
        } else if (blockedPoints.add(grain)) {
            return Optional.of(grain);
        } else
            return Optional.empty();

    }

}
