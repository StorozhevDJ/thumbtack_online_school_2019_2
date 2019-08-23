package net.thumbtack.school.base;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberOperations {

    /**
     * Ищет в массиве array первый элемент, значение которого равно value. Если
     * такое значение найдено, возвращает его позицию в массиве array, в противном
     * случае возвращает null.
     *
     * @param array
     * @param value
     * @return
     */
    public static Integer find(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return null;
    }

    /**
     * Ищет в массиве array первый элемент, значение которого по модулю не
     * отличается от value более чем на eps. Если такое значение найдено, возвращает
     * его позицию в массиве array, в противном случае возвращает null.
     *
     * @param array
     * @param value
     * @param eps
     * @return
     */
    public static Integer find(double[] array, double value, double eps) {
        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i] - value) < eps) {
                return i;
            }
        }
        return null;
    }

    /**
     * Вычисляет плотность вещества по формуле weight / volume. Если получившееся
     * значение не превышает max и не меньше min, возвращает полученное значение, в
     * противном случае возвращает null.
     *
     * @param weight
     * @param volume
     * @param min
     * @param max
     * @return
     */
    public static Double calculateDensity(double weight, double volume, double min, double max) {
        double p = weight / volume;
        return (p < max && p > min) ? p : null;
    }

    /**
     * Ищет в массиве array первый элемент, значение которого равно value. Если
     * такое значение найдено, возвращает его позицию в массиве array, в противном
     * случае возвращает null.
     *
     * @param array
     * @param value
     * @return
     */
    public static Integer find(BigInteger[] array, BigInteger value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Вычисляет плотность вещества по формуле weight / volume. Если получившееся
     * значение не превышает max и не меньше min, возвращает полученное значение, в
     * противном случае возвращает null.
     *
     * @param weight
     * @param volume
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal calculateDensity(BigDecimal weight, BigDecimal volume, BigDecimal min, BigDecimal max) {
        BigDecimal p = weight.divide(volume);
        return ((p.compareTo(min) == 1) && (p.compareTo(max) == -1)) ? p : null;
    }

}
