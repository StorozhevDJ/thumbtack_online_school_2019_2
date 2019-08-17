package net.thumbtack.school.introduction;

import java.util.stream.IntStream;

public class FirstSteps {

    public FirstSteps() {

    }

    public int sum(int x, int y) {
        return x + y;
    }

    public int mul(int x, int y) {
        final int i = x * y;
        return i;
    }

    public int div(int x, int y) {
        return x / y;
    }

    public int mod(int x, int y) {
        return x % y;
    }

    public boolean isEqual(int x, int y) {
        return x == y;
    }

    public boolean isGreater(int x, int y) {
        return x > y;
    }

    /**
     * Прямоугольник с горизонтальными и вертикальными сторонами, задан двумя точками - левой верхней (xLeft, yTop) и правой нижней (xRight, yBottom).
     * На плоскости OXY ось X направлена вправо, ось Y - вниз. Дана еще одна точка с координатами (x, y).
     * Гарантируется, что xLeft < xRight и yTop < yBottom.
     * Если точка лежит на границе прямоугольника, то считается, что она лежит внутри него.
     *
     * @param xLeft
     * @param yTop
     * @param xRight
     * @param yBottom
     * @param x
     * @param y
     * @return Метод должен возвращать true, если точка лежит внутри прямоугольника , иначе false.
     */
    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y) {
        if ((x > xRight) || (x < xLeft)) return false;
        return (y <= yBottom) && (y >= yTop);
    }

    public int sum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    public int mul(int[] array) {
        if (array.length == 0) return 0;
        int ret = 1;
        for (int i = 0; i < array.length; i++) {
            ret *= array[i];
        }
        return ret;
    }

    public int min(int[] array) {
        int val = Integer.MAX_VALUE;
        for (int x : array) {
            if (val > x) val = x;
        }
        return val;
    }

    public int max(int[] array) {
        int val = Integer.MIN_VALUE;
        for (int x : array) {
            if (val < x) val = x;
        }
        return val;
    }

    public double average(int[] array) {
        if (array.length == 0) return 0.0;
        double total = IntStream.of(array).sum();
        return total / array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        int i = Integer.MAX_VALUE;
        for (int j : array) {
            if (i <= j) return false;
            i = j;
        }
        return true;
    }

    public void cube(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] * array[i] * array[i];
        }
    }

    public boolean find(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }

    public void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
    }

    public boolean isPalindrome(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != array[array.length - 1 - i]) return false;
        }
        return true;
    }

    public int sum(int[][] matrix) {
        int sum = 0;
        for (int[] i : matrix) {
            for (int j : i) {
                sum += j;
            }
        }
        return sum;
    }

    public int max(int[][] matrix) {
        int val = Integer.MIN_VALUE;
        for (int[] array : matrix) {
            for (int x : array) {
                if (val < x) val = x;
            }
        }
        return val;
    }

    public int diagonalMax(int[][] matrix) {
        int val = Integer.MIN_VALUE;
        for (int i = 0; i < matrix[0].length; i++) {
            if (val < matrix[i][i]) val = matrix[i][i];
        }
        return val;
    }

    /**
     * Возвращает true, если все строки двумерного массива matrix строго упорядочены по убыванию, иначе false.
     * Пустая строка считается упорядоченной.
     * Разные строки массива matrix могут иметь разное количество элементов.
     * При написании метода рекомендуется внутри него вызвать метод из п. 13. (isSortedDescendant)
     *
     * @param matrix
     * @return
     */
    public boolean isSortedDescendant(int[][] matrix) {
        for (int[] a : matrix) {
            if (!isSortedDescendant(a)) return false;
        }
        return true;
    }
}
