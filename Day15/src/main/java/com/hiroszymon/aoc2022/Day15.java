package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@SuppressWarnings({"unused", "ReassignedVariable", "SuspiciousNameCombination", "SameParameterValue"})
public class Day15 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static final int rowToCheck = 2_000_000;
    private static final HashMap<Pair<Integer, Integer>, Integer> sensorToRadius = new HashMap<>();
    private static final ArrayList<Pair<Integer, Integer>> beacons = new ArrayList<>();


    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        final Set<Pair<Integer, Integer>> invalidPositions = new HashSet<>();
        for (String s : input) {
            String[] tmp = s.replaceAll("[^\\dx=\\-y,:]", "").split(":");
            int sensorX = Integer.parseInt(tmp[0].split(",")[0].replaceAll("[^\\d-]", "").trim());
            int sensorY = Integer.parseInt(tmp[0].split(",")[1].replaceAll("[^\\d-]", "").trim());
            int beaconX = Integer.parseInt(tmp[1].split(",")[0].replaceAll("[^\\d-]", "").trim());
            int beaconY = Integer.parseInt(tmp[1].split(",")[1].replaceAll("[^\\d-]", "").trim());
            Pair<Integer, Integer> beacon = Pair.of(beaconX, beaconY);
            Pair<Integer, Integer> sensor = Pair.of(sensorX, sensorY);
            int radius = Common.calculateManhattanDistance(sensor, beacon);
            sensorToRadius.put(sensor, radius);
            beacons.add(beacon);
        }

        List<Map.Entry<Pair<Integer, Integer>, Integer>> tmpSensorList = sensorToRadius.entrySet().stream().filter(e -> e.getKey().getValue() + e.getValue() > rowToCheck)
                .filter(e -> e.getKey().getValue() - e.getValue() < rowToCheck).toList();

        int minX = tmpSensorList.stream()
                .mapToInt(e -> (e.getKey().getKey() - e.getValue())).min().orElseThrow();
        int maxX = tmpSensorList.stream()
                .mapToInt(e -> (e.getKey().getKey() + e.getValue())).max().orElseThrow();


        for (int i = minX; i <= maxX; i++) {
            Pair<Integer, Integer> tmp = Pair.of(i, rowToCheck);
            for (Map.Entry<Pair<Integer, Integer>, Integer> e : tmpSensorList) {
                if (beacons.contains(tmp) || invalidPositions.contains(tmp) || sensorToRadius.containsKey(tmp))
                    continue;
                if (Common.calculateManhattanDistance(e.getKey(), tmp) <= e.getValue()) {
                    invalidPositions.add(tmp);
                }
            }
        }

        System.out.println("PART 1: Invalid positions: " + invalidPositions.size());
    }


    private static void part2(ArrayList<String> input) {
        int MAX_XY = 4_000_000;
        all:
        for (int x = 0; x < MAX_XY; x++) {
            outer1:
            for (int y = 0; y < MAX_XY; y++) {
                Pair<Integer, Integer> tmp = Pair.of(x, y);

                if (beacons.contains(tmp) || sensorToRadius.containsKey(tmp)) {
                    continue;
                }

                for (Map.Entry<Pair<Integer, Integer>, Integer> sensorDst : sensorToRadius.entrySet()) {
                    if (Common.calculateManhattanDistance(sensorDst.getKey(), tmp) <= sensorDst.getValue()) {
                        y = sensorDst.getKey().getValue() + sensorDst.getValue() - Math.abs(sensorDst.getKey().getKey() - x);
                        continue outer1;
                    }
                }

                System.out.println("PART 2: frequency: "+(4000000L*x+y));
                break all;

            }
        }


    }


}

