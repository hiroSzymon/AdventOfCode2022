package com.hiroszymon.aoc2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day1 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        HashMap<Integer, Long> elves = getElvesAndTheirCalories(input);

        elves.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(e -> System.out.printf("PART1 - MAX: Elf %d = %d Calories\n", e.getKey(), e.getValue()));


    }

    private static void part2(ArrayList<String> input) {
        HashMap<Integer, Long> elves = getElvesAndTheirCalories(input);

        System.out.printf("PART 2 - SUM OF TOP 3: %d Calories\n", elves.entrySet().stream()
                .sorted(Comparator.comparingLong(e -> -e.getValue()))
                .limit(3)
                .mapToLong(Map.Entry::getValue)
                .sum());
    }

    private static HashMap<Integer, Long> getElvesAndTheirCalories(ArrayList<String> input) {
        HashMap<Integer, Long> elves = new HashMap<>();

        for (int i = 0, j = 0; i < input.size(); i++) {
            if (!elves.containsKey(j)) {
                elves.put(j, 0L);
            }

            if (!input.get(i).trim().isEmpty())
                elves.merge(j, (long) Integer.parseInt(input.get(i).trim()), Long::sum);
            else
                j++;
        }

        return elves;
    }
}
