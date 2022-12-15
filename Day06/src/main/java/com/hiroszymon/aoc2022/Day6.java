package com.hiroszymon.aoc2022;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
public class Day6 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        String in = input.get(0);
        int i;
        for(i=0; i<in.length(); i++){
            String substring =in.substring(i, i+4);
            if(substring.chars().boxed().collect(Collectors.toSet()).size()==substring.length())
                break;
        }
        System.out.println("PART 1 Marker pos: "+(i+4));
    }

    private static void part2(ArrayList<String> input) {
        String in = input.get(0);
        int i;
        for(i=0; i<in.length(); i++){
            String substring =in.substring(i, i+14);
            if(substring.chars().boxed().collect(Collectors.toSet()).size()==substring.length())
                break;
        }
        System.out.println("PART 2 Marker pos: "+(i+14));
    }


}
