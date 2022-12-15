package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("SameParameterValue")
public class Day8 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());

    public static void main(String[] args) {
        part1(input);
        part2(input);
    }

    private static void part1(ArrayList<String> input) {
        HashSet<Pair<Integer, Integer>> visibleTrees = new HashSet<>();
        int[][] trees = getTrees(input);

        int treesOnEdge = (trees.length - 2) * 2 + (trees[0].length - 2) * 2 + 4;

        for (int row = 1; row < trees.length - 1; row++) {
            for (int col = 1; col < trees[row].length - 1; col++) {
                if (checkTreeVisibility(trees, row, col)) {
                    visibleTrees.add(Pair.of(row, col));
                }
            }
        }

        System.out.println("PART 1: visible from outside: "+ (visibleTrees.size() + treesOnEdge));

    }

    private static void part2(ArrayList<String> input) {
        int[][] trees = getTrees(input);
        long maxScenicScore = 0;

        for (int row = 0; row < trees.length; row++) {
            for (int col = 0; col < trees[row].length; col++) {
                long currentScore = getTreeScenicScore(trees, row, col);
                if (currentScore>maxScenicScore) {
                    maxScenicScore=currentScore;
                }
            }
        }

        System.out.println("PART 2: max scenic score: "+ maxScenicScore);

    }

    private static int[][] getTrees(ArrayList<String> input) {
        int[][] trees = new int[input.size()][input.get(0).length()];
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).length(); col++) {
                trees[row][col] = input.get(row).charAt(col) - '0';
            }
        }

        return trees;
    }

    private static Boolean checkTreeVisibility(int[][] grid, int row, int col) {
        boolean visibleTop = true;
        boolean visibleBottom = true;
        boolean visibleLeft = true;
        boolean visibleRight = true;

        int height = grid[row][col];
        for (int i = 0; i < row; i++) {
            if (grid[i][col] >= height) {
                visibleTop = false;
                break;
            }
        }

        for (int i = row + 1; i < grid.length; i++) {
            if (grid[i][col] >= height) {
                visibleBottom = false;
                break;
            }
        }

        for (int i = 0; i < col; i++) {
            if (grid[row][i] >= height) {
                visibleLeft = false;
                break;
            }
        }

        for (int i = col + 1; i < grid[row].length; i++) {
            if (grid[row][i] >= height) {
                visibleRight = false;
                break;
            }
        }

        return visibleTop | visibleBottom | visibleLeft | visibleRight;

    }

    private static long getTreeScenicScore(int[][] grid, int row, int col) {
        int viewTop = 0;
        int viewBottom = 0;
        int viewLeft = 0;
        int viewRight = 0;

        int height = grid[row][col];
        for (int i = row-1; i >= 0; i--) {
            if (grid[i][col] < height) {
                viewTop++;
            } else {
                viewTop++;
                break;
            }

        }

        for (int i = row + 1; i < grid.length; i++) {
            if (grid[i][col] < height) {
                viewBottom++;
            } else {
                viewBottom++;
                break;
            }
        }

        for (int i = col-1; i >= 0; i--) {
            if (grid[row][i] < height) {
                viewLeft++;
            } else {
                viewLeft++;
                break;
            }
        }

        for (int i = col + 1; i < grid[row].length; i++) {
            if (grid[row][i] < height) {
                viewRight++;
            } else {
                viewRight++;
                break;
            }
        }

        return (long) viewTop *viewBottom*viewLeft*viewRight;
    }

}
