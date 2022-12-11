package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day11 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        ArrayList<Monkey> monkeys = fillMonkeys(input);

        for (int i = 0; i < 20; i++) {
            for (Monkey m : monkeys) {
                m.monkeyRound(monkeys, 3);
            }
        }

        List<Long> result = monkeys.stream().map(Monkey::getInspectedItemsCount).sorted(Collections.reverseOrder()).limit(2).toList();
        System.out.println("PART 1: result: " + result.get(0)*(result.get(1)));
    }

    private static void part2(ArrayList<String> input) {
        Monkey.resetCommonMod();
        ArrayList<Monkey> monkeys = fillMonkeys(input);

        for (int i = 0; i < 10000 ; i++) {
            for (Monkey m : monkeys) {
                m.monkeyRound(monkeys, 1);
            }
        }

        List<Long> result = monkeys.stream().map(Monkey::getInspectedItemsCount).sorted(Collections.reverseOrder()).limit(2).toList();
        System.out.println("PART 2: result: " + result.get(0)*(result.get(1)));
    }

    private static ArrayList<Monkey> fillMonkeys(ArrayList<String> input) {
        ArrayList<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < input.size(); i += 7) {
            ArrayDeque<Long> items = new ArrayDeque<>();
            for (String s : input.get(i + 1).split(":")[1].split(","))
                items.push(Long.parseLong(s.trim()));
            String[] operation = input.get(i + 2).split("=")[1].trim().split(" ");
            int testDivisor = Integer.parseInt(input.get(i + 3).replaceAll("\\D", "").trim());
            int monkeyTrue = Integer.parseInt(input.get(i + 4).replaceAll("\\D", "").trim());
            int monkeyFalse = Integer.parseInt(input.get(i + 5).replaceAll("\\D", "").trim());
            monkeys.add(new Monkey(items, operation, testDivisor, Pair.of(monkeyTrue, monkeyFalse)));

        }

        return monkeys;
    }


}
