package com.lineate.function;

@FunctionalInterface
public interface MyFunction<K, T> {
	/**
     * 10. Создайте интерфейс MyFunction, декларирующий единственный метод K
     * apply(T arg). Замените Function на MyFunction.
     */
	K apply(T t);
	
	/**
	 * 11. Предположим вы решили использовать функцию с двумя аргументами. Что
	 * произойдет когда вы добавите K apply(T arg1, T arg2)? Задекларируйте
	 * MyFunction как функциональный интерфейс. Попробуйте скомпилировать.
	 */
	//K apply(T arg1, T arg2);
}
