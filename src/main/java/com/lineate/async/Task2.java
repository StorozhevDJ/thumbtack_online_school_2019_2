package com.lineate.async;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Есть два файла
 * file1.txt
 * 1
 * 2
 * …
 * 10
 * <p>
 * file2.txt
 * 1000
 * 2000
 * …
 * 10000
 * <p>
 * С помощью CompletableFuture реализовать параллельное чтение, затем записать два файла с суммой и произведением этих чисел, т.е.
 * 1001
 * 2002
 * …
 * 10010
 * <p>
 * 1000
 * 4000
 * …
 * 100000
 */

public class Task2 {

    private static CompletableFuture<List<Integer>> fileReader(File file) {
        List<Integer> fileArray = new ArrayList<>();
        return CompletableFuture.supplyAsync(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    fileArray.add(Integer.parseInt(line));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileArray;
        });
    }

    private static File fileWriter(List<Integer> toFile, String fileName) {
        File file = new File(fileName);
        CompletableFuture.supplyAsync(() -> {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {

                for (Integer integer : toFile) {
                    writer.write(integer);
                    writer.newLine();
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file;
        }).handle((r, e) -> {
            System.out.println("result " + r + "\nexception " + e);
            return r;
        });
        return file;
    }

    public static void main(String[] args) {

        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");

        //sumAndCompositionFiles(file1, file2);
        CompletableFuture<List<Integer>> file1Reader = fileReader(file1);
        CompletableFuture<List<Integer>> file2Reader = fileReader(file2);

        file1Reader.thenCombineAsync(file2Reader, (a, b) -> {
            List<Integer> summa = new ArrayList<>();
            for (int i = 0; i < a.size(); i++) {
                summa.add(a.get(i) + b.get(i));
            }
            return summa;
        }).handle((r, e) -> {
            System.out.println("result " + r + "\nexception " + e);
            return r;
        });

        file1Reader.thenCombineAsync(file2Reader, (a, b) -> {
            List<Integer> comp = new ArrayList<>();
            for (int i = 0; i < a.size(); i++) {
                comp.add(a.get(i) * b.get(i));
            }
            return comp;
        }).handle((r, e) -> {
            System.out.println("result " + r + "\nexception " + e);
            return r;
        });
    }
}
