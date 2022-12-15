package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Common {
    public static List<String> readInput() {
        try {
            return Files.readAllLines(Path.of("input.txt")).stream().filter(e->!e.startsWith("#")).toList();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
            return Collections.emptyList();
        }
    }

    static int calculateManhattanDistance(Pair<Integer, Integer> sensor, Pair<Integer, Integer> beacon) {
        return Math.abs(sensor.getKey() - beacon.getKey()) + Math.abs(sensor.getValue() - beacon.getValue());
    }
}
