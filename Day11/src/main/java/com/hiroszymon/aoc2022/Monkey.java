package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Monkey {
    private static long commonMod = 1;
    private final ArrayDeque<Long> items;
    private long inspectedItemsCount = 0;
    private final String[] operation;
    private final int testDivisor;
    private final Pair<Integer, Integer> targetMonkeysTF;

    public Monkey(ArrayDeque<Long> items, String[] operation, int testDivisor, Pair<Integer, Integer> targetMonkeysTF) {
        this.items = items;
        this.testDivisor = testDivisor;
        this.targetMonkeysTF = targetMonkeysTF;
        this.operation = operation;
        commonMod *= testDivisor;
    }

    public void monkeyRound(ArrayList<Monkey> monkeys, int divBy) {
        Long tmp;
        while ((tmp = items.pollLast()) != null) {
            inspectedItemsCount++;

            tmp = monkeyAction(tmp, divBy);
            if (tmp % testDivisor == 0) {
                monkeys.get(targetMonkeysTF.getKey()).addItem(tmp % commonMod);
            } else
                monkeys.get(targetMonkeysTF.getValue()).addItem(tmp % commonMod);
        }
    }
    
    private long monkeyAction(long op, int div) {
        long op1, op2;
        if (StringUtils.isNumeric(operation[0].trim())) {
            op1 = Long.parseLong(operation[0]);
            op2 = op;
        } else if (StringUtils.isNumeric(operation[2].trim())) {
            op1 = op;
            op2 = Long.parseLong(operation[2]);
        } else {
            op1 = op2 = op;
        }

        return switch (operation[1]) {
            case "*" -> (op1 * op2) / (div);
            case "+" -> (op1 + op2) / (div);
            default -> 0L;
        };
    }

    public void addItem(long item) {
        items.push(item);
    }

    public long getInspectedItemsCount() {
        return inspectedItemsCount;
    }

    public static void resetCommonMod(){
        commonMod=1;
    }
}
