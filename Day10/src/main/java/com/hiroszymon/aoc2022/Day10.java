package com.hiroszymon.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;

public class Day10 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static int cycle = 0;
    private static long regX = 1;

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        ArrayList<Long> signalStrengths = new ArrayList<>();
        for (String s : input) {
            String opcode = s.split(" ")[0];


            switch (opcode) {
                case "noop" -> incrementCycleAndAddToList(signalStrengths);
                case "addx" -> {
                    incrementCycleAndAddToList(signalStrengths);
                    incrementCycleAndAddToList(signalStrengths);
                    regX += Integer.parseInt(s.split(" ")[1].trim());
                }
            }

        }
        System.out.println("PART 1: sum of strengths: " + signalStrengths.stream().mapToLong(e -> e).sum());
    }

    private static void incrementCycleAndAddToList(ArrayList<Long> signalStrengths) {
        cycle += 1;
        if ((cycle / 20f) % 2 == 1) {
            signalStrengths.add(cycle * regX);
        }
    }


    private static void part2(ArrayList<String> input) {
        cycle = 0;
        regX = 1;
        char[] line = new char[40];
        Arrays.fill(line, ' ');

        System.out.println("PART 2: ");
        for (String s : input) {
            String opcode = s.split(" ")[0];


            switch (opcode) {
                case "noop" -> drawAndIncrementCycle(line);
                case "addx" -> {
                    drawAndIncrementCycle(line);
                    drawAndIncrementCycle(line);
                    regX += Integer.parseInt(s.split(" ")[1].trim());
                }
            }
        }
    }

    private static void drawAndIncrementCycle(char[] line) {
        long spriteMiddlePosition = regX;
        if (spriteMiddlePosition - 1 == cycle % 40 || spriteMiddlePosition == cycle % 40 || spriteMiddlePosition + 1 == cycle % 40) {
            line[cycle % 40] = '#';
        }
        cycle += 1;

        if (cycle > 0 && cycle % 40 == 0) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
            Arrays.fill(line, ' ');
        }
    }
}
