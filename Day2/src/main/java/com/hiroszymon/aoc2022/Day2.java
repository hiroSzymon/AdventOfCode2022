package com.hiroszymon.aoc2022;

import java.util.ArrayList;

public class Day2 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        long result = input.stream().mapToLong(e -> play(e.split("\\s")[0].trim(), e.split("\\s")[1].trim())).sum();
        System.out.println("PART1 Result: " + result);
    }

    private static void part2(ArrayList<String> input) {
        long result = input.stream().mapToLong(e -> play(e.split("\\s")[0].trim(),
                modifyYouForPart2(e.split("\\s")[0].trim(), e.split("\\s")[1].trim()))).sum();
        System.out.println("PART2 Result: " + result);
    }

    private static int play(String opponent, String you) {
        String youNormalized = switch (you) {
            case "X":
                yield "A";
            case "Y":
                yield "B";
            case "Z":
                yield "C";
            default:
                yield you;
        };

        int youValue = switch (youNormalized) {
            case "A":
                yield 1;
            case "B":
                yield 2;
            case "C":
                yield 3;
            default:
                throw new UnsupportedOperationException();
        };

        int retVal = youNormalized.compareTo(opponent);
        //noinspection ComparatorResultComparison
        if(retVal>=2 || retVal<=-2)
            retVal=-retVal;

        if(retVal > 0){
            return youValue + 6;
        }else if(retVal == 0) {
            return youValue + 3;
        }else {
            return youValue;
        }

    }

    private static String modifyYouForPart2(String opponent, String you){
        return switch (you){
            case "Y":
                yield opponent;
            case "X":
                yield String.valueOf((char)('A'+(char)(((opponent.charAt(0)-1))+1)%3));
            case "Z":
                yield String.valueOf((char)('A'+(char)(((opponent.charAt(0)+1))+1)%3));
            default:
                throw new UnsupportedOperationException();
        };
    }


}
