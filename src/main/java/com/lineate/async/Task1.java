package com.lineate.async;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class Task1 {

    public static void getCorrelationCoefficient(int[] a, int[] b, int n) {
        CompletableFuture<Integer> sumArrayA = CompletableFuture.supplyAsync(() -> Arrays.stream(a).sum());
        CompletableFuture<Integer> sumArrayB = CompletableFuture.supplyAsync(() -> Arrays.stream(b).sum());
        CompletableFuture<Integer> sumAA = CompletableFuture.supplyAsync(() -> {
            int[] c = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                c[i] = a[i] * a[i];
            }
            return Arrays.stream(c).sum() * n;
        });
        CompletableFuture<Integer> sumBB = CompletableFuture.supplyAsync(() -> {
            int[] d = new int[b.length];
            for (int j = 0; j < b.length; j++) {
                d[j] = b[j] * b[j];
            }
            return Arrays.stream(d).sum() * n;
        });
        CompletableFuture<Integer> sumAB = CompletableFuture.supplyAsync(() -> {
            int[] e = new int[a.length];
            for (int k = 0; k < a.length; k++) {
                e[k] = a[k] * b[k];
            }
            return Arrays.stream(e).sum() * n;
        });
        CompletableFuture<Integer> numerator = sumArrayA.thenCombineAsync(sumArrayB, (q, w) -> q * w)
                .thenCombineAsync(sumAB, (r, t) -> t - r);
        CompletableFuture<Double> denominatorSqrt = sumArrayA.thenCombineAsync(sumAA, (x, z) -> Math.sqrt(z - (x * x)));
        CompletableFuture<Double> coeff = sumArrayB.thenCombineAsync(sumBB, (x, z) -> z - (x * x))
                .thenCombineAsync(denominatorSqrt, (q, w) -> q * w)
                .thenCombineAsync(numerator, (r, t) -> t / r)
                .handle((r, e) -> {
                    System.out.println("result " + r + "\nexception " + e);
                    return r;
                });
    }

    public static void main(String[] args) {

        int[] a = {1, 2, 3, 4, 5};
        int[] b = {6, 7, 8, 9, 10};
        int n = 5;

        getCorrelationCoefficient(a, b, n);
    }
}
