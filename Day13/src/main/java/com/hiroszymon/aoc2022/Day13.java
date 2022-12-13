package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public class Day13 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        ArrayList<Pair<Input, Input>> comparePairs = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i).isEmpty())
                continue;
            Input input1 = new Input(input.get(i));
            Input input2 = new Input(input.get(++i));
            comparePairs.add(Pair.of(input1, input2));
        }
        int sum = 0;
        for (int i = 0; i < comparePairs.size(); i++) {
            if (comparePairs.get(i).getKey().compareTo(comparePairs.get(i).getValue()) > 0)
                sum += (i + 1);
        }
        System.out.println("PART 1: sum: " + sum);

    }


    private static void part2(ArrayList<String> input) {
        ArrayList<Input> inputs = new ArrayList<>();
        for (String s : input) {
            if (s.isEmpty())
                continue;
            Input tmp = new Input(s);
            inputs.add(tmp);
        }

        Input div1 = new Input("[[2]]");
        Input div2 = new Input("[[6]]");
        inputs.add(div1);
        inputs.add(div2);

        Collections.sort(inputs);
        Collections.reverse(inputs);

        System.out.println("PART 2: key: "+((inputs.indexOf(div1)+1) * (inputs.indexOf(div2)+1)));

    }

    private static class Input implements Comparable<Input> {
        private final ArrayList<Input> subinputs;
        private Integer value = null;

        private boolean intOnly = true;

        public Input(String s) {
            subinputs = new ArrayList<>();
            if (s.equals("[]")) {
                value = -1;
            }
            if (!s.startsWith("[")) {
                value = Integer.parseInt(s);
            } else {
                s = s.substring(1, s.length() - 1);
                int level = 0;
                StringBuilder tmp = new StringBuilder();
                for (char c : s.toCharArray()) {
                    if (c == ',' && level == 0) {
                        subinputs.add(new Input(tmp.toString()));
                        tmp = new StringBuilder();
                    } else {
                        level += (c == '[') ? 1 : (c == ']') ? -1 : 0;
                        tmp.append(c);
                    }
                }
                if (!tmp.toString().equals("")) {
                    subinputs.add(new Input(tmp.toString()));
                }
                intOnly = false;
            }
        }

        public int compareTo(Input rightInput) {
            if (intOnly && rightInput.intOnly) {
                return rightInput.value - value;
            }
            if (!intOnly && !rightInput.intOnly) {
                for (int i = 0; i < Math.min(subinputs.size(), rightInput.subinputs.size()); i++) {
                    int val = subinputs.get(i).compareTo(rightInput.subinputs.get(i));
                    if (val != 0) {
                        return val;
                    }
                }
                return rightInput.subinputs.size() - subinputs.size();
            }
            Input lst1 = intOnly ? new Input("[" + value + "]") : this;
            Input lst2 = rightInput.intOnly ? new Input("[" + rightInput.value + "]") : rightInput;
            return lst1.compareTo(lst2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Input input = (Input) o;
            return Objects.equals(subinputs, input.subinputs) && Objects.equals(value, input.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subinputs, value);
        }
    }


}
