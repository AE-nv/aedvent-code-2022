package io.dev.aoc_2022.day9;

import io.dev.aoc.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

class Day9 {

    static Position[] followMotion(Position[] rope, String command, Set<Position> visitedByTail) {
        char direction = command.charAt(0);
        int amount = Integer.parseInt(command.substring(1).trim());
        Position[] newPositions = rope.clone();
        for (int i = 0; i < amount; i++) {
            newPositions[0] = move(newPositions[0], direction);
            for (int j = 1; j < newPositions.length; j++) {
                newPositions[j] = newPositions[j].determineNewPosition(newPositions[j - 1]);
            }
            visitedByTail.add(newPositions[newPositions.length - 1]);
        }
        return newPositions;
    }

    static Position move(Position start, char direction) {
        int xIncrement = switch (direction) {
            case 'R' -> 1;
            case 'L' -> -1;
            default -> 0;
        };

        int yIncrement = switch (direction) {
            case 'U' -> 1;
            case 'D' -> -1;
            default -> 0;
        };

        return new Position(start.x() + xIncrement, start.y() + yIncrement);
    }

    static int partA(String fileName) throws IOException {
        return moveRope(fileName, 2);
    }

    static int partB(String fileName) throws IOException {
        return moveRope(fileName, 10);
    }

    private static int moveRope(String fileName, int ropeLenght) throws IOException {
        Position[] rope = new Position[ropeLenght];
        Arrays.setAll(rope, i -> new Position(0, 0));

        Set<Position> visitedByTail = new HashSet<>();
        visitedByTail.add(rope[rope.length - 1]);

        List<String> strings = Files.readAllLines(FileUtils.getNullSafePath(fileName));

        for (String line : strings) {
            rope = followMotion(rope, line, visitedByTail);
        }

        return visitedByTail.size();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Part A : " + partA("day9.txt"));
        System.out.println("Part B : " + partB("day9.txt"));
    }

}

record Position(int x, int y) {

    Position determineNewPosition(Position head) {

        if (abs(head.x - x) > 1 && abs(head.y - y) > 1) {
            return new Position(x + (head.x < x ? -1 : 1), y + (head.y < y ? -1 : 1));
        }

        if (abs(head.x - x) > 1) {
            return new Position(x + (head.x < x ? -1 : 1), head.y);
        }

        if (abs(head.y - y) > 1) {
            return new Position(head.x, y + (head.y < y ? -1 : 1));
        }

        return this;
    }
}
