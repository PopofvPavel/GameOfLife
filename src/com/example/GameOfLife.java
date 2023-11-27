package com.example;

import java.util.Scanner;

public class GameOfLife {
    private static final int n = 10;
    private static final int m = 10;
    private static char[][] field = new char[n][m];

    public static void main(String[] args) {

        fillDead();

        System.out.println("Введите начальные живые клетки (координаты через пробел, индекс начинается с 0).\n" +
                " Для завершения ввода введите -1 -1:");

        Scanner scanner = new Scanner(System.in);
        int x;
        int y;

        do {
            System.out.print("Введите координаты: ");
            x = scanner.nextInt();
            y = scanner.nextInt();

            if (isValidCoordinate(x, y, field.length)) {
                field[x][y] = 'O';
            } else {
                System.out.println("Некорректные координаты. Повторите ввод.");
            }

        } while (x != -1 && y != -1);

        System.out.println("Исходное поле:");
        printField(field);

        int step = 0;
        while (true) {
            updateField();

            try {
                Thread.sleep(1000);
                step++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Step " + step);
            printField(field);

        }


    }

    private static void updateField() {

        char[][] newField = new char[GameOfLife.n][GameOfLife.m];
        for (int i = 0; i < GameOfLife.n; i++) {
            for (int j = 0; j < GameOfLife.m; j++) {
                int neighbours = getAliveNeighboursCounter(i, j);
                if ((field[i][j] == 'X') && (neighbours == 3)) {
                    newField[i][j] = 'O';
                } else if ((field[i][j] == 'O') && (neighbours < 2)) {
                    newField[i][j] = 'X';
                } else if ((field[i][j] == 'O') && (neighbours > 3)) {
                    newField[i][j] = 'X';
                } else {
                    newField[i][j] = field[i][j];
                }

            }
        }
        field = newField;
    }

    private static int getAliveNeighboursCounter(int x, int y) {
        int count = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                try {
                    if ((i == x) && (j == y)) {
                        continue;
                    }
                    if (field[i][j] == 'O') {
                        count++;
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    continue;
                }

            }
        }
        return count;
    }

    private static boolean isValidCoordinate(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private static void printField(char[][] field) {
        for (char[] row : field) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static void fillDead() {
        for (int i = 0; i < GameOfLife.n; i++) {
            for (int j = 0; j < GameOfLife.m; j++) {
                field[i][j] = 'X';
            }
        }
    }
}
