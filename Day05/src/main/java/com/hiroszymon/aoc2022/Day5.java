package com.hiroszymon.aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
public class Day5 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        ArrayList<ArrayDeque<String>> stacks = new ArrayList<>();
        int emptyLineIndex = initialize(input, stacks);

        for (int i = emptyLineIndex + 1; i < input.size(); i++) {
            modifyStacks(stacks, input.get(i).trim());
        }

        System.out.println("PART 1: Top of stack: " + stacks.stream().map(ArrayDeque::poll).collect(Collectors.joining()).replaceAll("[\\[\\]]", ""));
    }

    private static void modifyStacks(ArrayList<ArrayDeque<String>> stacks, String operation) {
        String[] operations = operation.replaceAll("\\D", " ").trim().split("\\s+");
        int[] operationsAsNumber = new int[operations.length];
        for (int i = 0; i < operations.length; i++) {
            operationsAsNumber[i] = Integer.parseInt(operations[i]);
        }

        for (int i = operationsAsNumber[0]; i > 0; i--) {
            stacks.get(operationsAsNumber[2] - 1).push(stacks.get(operationsAsNumber[1] - 1).pop());
        }

    }

    private static void part2(ArrayList<String> input) {
        ArrayList<ArrayDeque<String>> stacks = new ArrayList<>();
        int emptyLineIndex = initialize(input, stacks);

        for (int i = emptyLineIndex + 1; i < input.size(); i++) {
            modifyStacksV2(stacks, input.get(i).trim());
        }

        System.out.println("PART 2: Top of stack: " + stacks.stream().map(ArrayDeque::poll).collect(Collectors.joining()).replaceAll("[\\[\\]]", ""));
    }

    private static void modifyStacksV2(ArrayList<ArrayDeque<String>> stacks, String operation) {
        String[] operations = operation.replaceAll("\\D", " ").trim().split("\\s+");
        int[] operationsAsNumber = new int[operations.length];
        for (int i = 0; i < operations.length; i++) {
            operationsAsNumber[i] = Integer.parseInt(operations[i]);
        }

        ArrayDeque<String> craneBuffer = new ArrayDeque<>();
        for (int i = operationsAsNumber[0]; i > 0; i--) {
            craneBuffer.addFirst(stacks.get(operationsAsNumber[1] - 1).pop());
        }
        for (String ignored : craneBuffer) {
            stacks.get(operationsAsNumber[2] - 1).push(craneBuffer.pop());
        }

    }

    private static int initialize(ArrayList<String> input, ArrayList<ArrayDeque<String>> stacks) {
        int emptyLineIndex;
        for (emptyLineIndex = 0; emptyLineIndex < input.size(); emptyLineIndex++) {
            if (input.get(emptyLineIndex).isEmpty())
                break;
        }

        for (String ignored : input.get(emptyLineIndex - 1).split("\\s+")) {
            if (ignored.trim().isEmpty())
                continue;
            stacks.add(new ArrayDeque<>());
        }

        for (int i = emptyLineIndex - 2; i >= 0; i--) {
            String[] stackValues = new String[stacks.size()];
            for (int c = 0, s = 0; s < stackValues.length; s++, c += 4) {
                stackValues[s] = input.get(i).substring(c, c + 3);
            }

            for (int s = 0; s < stackValues.length; s++) {
                if (stackValues[s].trim().isEmpty())
                    continue;
                stacks.get(s).addFirst(stackValues[s]);
            }
        }
        return emptyLineIndex;
    }


}
