package net.thumbtack.school.matrix;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MatrixNonSimilarRows {

    private List<int[]> matrixNonSimilarRows;// = new ArrayList<int[]>();

    /**
     * Создает MatrixNonSimilarRows по заданной матрице.
     *
     * @return
     */
    public MatrixNonSimilarRows(int[][] matrix) {
        Map<Set<Integer>, int[]> map = Arrays.stream(matrix)
                .collect(Collectors.toMap((int[] row) -> Arrays.stream(row).boxed().collect(Collectors.toSet()),
                        Function.identity(), (r1, r2) -> r2));
        matrixNonSimilarRows = new ArrayList<>(map.values());
        //matrixNonSimilarRows.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    /**
     * Возвращает список непохожих строк.
     *
     * @return
     */
    public List<int[]> getNonSimilarRows() {
        return matrixNonSimilarRows;
    }

}
