package com.hiroszymon.aoc2022;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("SameParameterValue")
public class Day7 {
    private static final ArrayList<String> input = new ArrayList<>(Common.readInput());
    private static final ArrayList<CustomFile> files = new ArrayList<>();
    private static CustomFile currentDir;
    private static CustomFile rootDir;
    private static final long PART1_MAX_SIZE = 100000L;
    private static final long PART2_FILESYSTEM_SIZE = 70000000L;
    private static final long PART2_FREE_SPACE_NEEDED = 30000000;

    public static void main(String[] args) {
        init(input);
        part1();
        part2();
    }

    private static void part1() {
        System.out.println("PART 1: Sum: " + files.stream().filter(e -> e.isDirectory() && e.getSize() < PART1_MAX_SIZE)
                .mapToLong(CustomFile::getSize).sum());
    }

    private static void part2() {
        long currentAvailableSpace = PART2_FILESYSTEM_SIZE - rootDir.getSize();
        long spaceNeeded = PART2_FREE_SPACE_NEEDED - currentAvailableSpace;
        long removedFileSize = files.stream().filter(e -> e.isDirectory() && e.getSize() >= spaceNeeded).
                min(Comparator.comparingLong(CustomFile::getSize)).map(CustomFile::getSize).orElse(-1L);

        System.out.println("PART 2: Size: " + removedFileSize);
        System.out.println("PART 2: free space after delete: " + (currentAvailableSpace + removedFileSize));
    }

    private static void init(ArrayList<String> input) {
        for (String line : input) {
            String[] elements = line.split("\\s");
            if (elements[0].equals("$")) {
                executeCommand(elements[1], elements.length > 2 ? elements[2] : "");
            } else {
                parseOutput(elements);
            }
        }
    }

    private static void executeCommand(String command, String... params) {
        switch (command) {
            case "cd":
                if (params[0].equals("..")) {
                    currentDir = currentDir.getParent();
                } else {
                    if (currentDir == null) {
                        CustomFile dir = CustomFile.newDir(params[0], null);
                        rootDir = dir;
                        currentDir = dir;
                    } else {
                        currentDir = currentDir.getChildDir(params[0]);
                    }

                }
                break;
            case "ls":
                break;
        }
    }

    private static void parseOutput(String[] elements) {
        CustomFile file;
        if (StringUtils.isNumeric(elements[0])) {
            file = CustomFile.newFile(elements[1], currentDir, Long.parseLong(elements[0]));
        } else {
            file = CustomFile.newDir(elements[1], currentDir);
        }
        files.add(file);
    }

}
