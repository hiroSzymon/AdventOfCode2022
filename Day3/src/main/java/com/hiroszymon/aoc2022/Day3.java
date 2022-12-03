package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day3 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        ArrayList<Pair<HashSet<Character>, HashSet<Character>>> rucksacksCompartments  =new ArrayList<>();
        for(String s : input){
            HashSet<Character> compartment1 = s.substring(0, s.length()/2).chars()
                    .mapToObj(e -> (char) e).collect(Collectors.toCollection(HashSet::new));
            HashSet<Character> compartment2 = s.substring(s.length()/2).chars()
                    .mapToObj(e -> (char) e).collect(Collectors.toCollection(HashSet::new));

            rucksacksCompartments.add(Pair.of(compartment1, compartment2));
        }
        System.out.println("PART1 - sum: "+ rucksacksCompartments.stream().mapToLong(e->{
            e.getKey().retainAll(e.getValue());
            return e.getKey().stream().mapToInt(c->c>90?c-96:c-38).sum(); //ascii to priority; the ?: because lowercase have LOWER priority
        }).sum());
    }

    private static void part2(ArrayList<String> input) {
        ArrayList<Triple<HashSet<Character>, HashSet<Character>, HashSet<Character>>> rucksacks  =new ArrayList<>();
        for(int i=0; i<input.size(); i+=3){
            HashSet<Character> rucksack1 = input.get(i).chars()
                    .mapToObj(e -> (char) e).collect(Collectors.toCollection(HashSet::new));
            HashSet<Character> rucksack2 = input.get(i+1).chars()
                    .mapToObj(e -> (char) e).collect(Collectors.toCollection(HashSet::new));
            HashSet<Character> rucksack3 = input.get(i+2).chars()
                    .mapToObj(e -> (char) e).collect(Collectors.toCollection(HashSet::new));

            rucksacks.add(Triple.of(rucksack1, rucksack2, rucksack3));
        }

        System.out.println("PART2 - sum: "+ rucksacks.stream().mapToLong(e->{
            e.getLeft().retainAll(e.getMiddle());
            e.getLeft().retainAll(e.getRight());
            return e.getLeft().stream().mapToInt(c->c>90?c-96:c-38).sum(); //ascii to priority; the ?: because lowercase have LOWER priority
        }).sum());

    }


}
