package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("SameParameterValue")
public class Day4 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        long fullyContainedPairs = 0;
        for(String s : input){
            String firstPair =  s.split(",")[0];
            String secondPair =  s.split(",")[1];
            Pair<Integer, Integer> firstPairInt = Pair.of(Integer.parseInt(firstPair.split("-")[0]), Integer.parseInt(firstPair.split("-")[1]));
            Pair<Integer, Integer> secondPairInt = Pair.of(Integer.parseInt(secondPair.split("-")[0]), Integer.parseInt(secondPair.split("-")[1]));

            if((firstPairInt.getKey()<=secondPairInt.getKey() && firstPairInt.getValue()>= secondPairInt.getValue() ) ||
                    ( secondPairInt.getKey()<=firstPairInt.getKey() && secondPairInt.getValue()>=firstPairInt.getValue()))
                fullyContainedPairs++;

        }

        System.out.println("PART 1 Sum: "+fullyContainedPairs);

    }

    private static void part2(ArrayList<String> input) {
        long overlappingPairs = 0;
        for(String s : input){
            String firstPair =  s.split(",")[0];
            String secondPair =  s.split(",")[1];

            HashSet<Integer> firstRange = rangeToSet(firstPair);
            HashSet<Integer> secondRange = rangeToSet(secondPair);

            boolean modified;
            if(firstRange.size()>secondRange.size()){
                modified = firstRange.retainAll(secondRange);

            }else {
                modified = secondRange.retainAll(firstRange);
            }

            boolean condition = (modified && firstRange.size()!=0 && secondRange.size()!=0) || firstRange.equals(secondRange) ;
            if(condition)
                overlappingPairs++;


        }

        System.out.println("PART 2 Sum: "+overlappingPairs);
    }

    private static HashSet<Integer> rangeToSet(String range){
        int from, to;
        from = Integer.parseInt(range.split("-")[0].trim());
        to = Integer.parseInt(range.split("-")[1].trim());

        HashSet<Integer> resultSet = new HashSet<>();
        for(int i = from; i<to+1 ;i++){
            resultSet.add(i);
        }
        return resultSet;
    }


}
