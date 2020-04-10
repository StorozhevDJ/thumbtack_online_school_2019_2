package com.lineate.threads;



public class Task6 {

    /**
     * Можно ли корректно решить задачу 4 , используя Collections. synchronizedList и не используя synchronized явно?
     * Если да - напишите программу.
     * <p>
     * Collections.synchronizedList в сравнении с ArrayList имеет синхронизированные операции add, remove и т.п.
     * Но итератор в данном случае не синхронизирован.
     * Поэтому, при использовании итератора, synchronizedList нужно обязательно синхронизировать вручную.
     *
     * @param args
     */
    public static void main(String args[]) {

    }
}
