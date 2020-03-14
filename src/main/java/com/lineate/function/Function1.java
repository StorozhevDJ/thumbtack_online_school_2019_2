package com.lineate.function;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Function1 {

	/**
	 * 1. Используя функциональный интерфейс java.util.function.Function и
	 * лямбда-выражения, создайте: 1.a функцию split (String s) -> List<String>,
	 * разбивающую строку по пробелам
	 * 
	 * 2. Попробуйте избавиться от декларации типов в параметрах функций из пункта
	 * 1. Почему это возможно?
	 */
	public List<?> splitStringToList(String string) {
		Function<String, List<?>> split = t -> Arrays.asList((t.split(" ")));
		return split.apply(string);
	}

	/**
	 * 1.b функцию count (List<?> list) -> Integer, считающую количество элементов в
	 * любом списке
	 * 
	 * 3. Попробуйте заменить лямбда-выражение на method reference, в каких случаях
	 * это возможно и почему?
	 */
	public int getCountFromStringList(List<String> stringList) {
		Function<List<?>, Integer> count = List::size;
		return count.apply(stringList);
	}

	/**
	 * 4. Перепишите решение из п. 1, композируя функции split и count при помощи
	 * 4.a default-методов интерфейса Function, в новую функцию splitAndCount
	 * 
	 * используйте andThen
	 */
	public int getCountFromStringAndThen(String string) {
		Function<String, List<String>> split = t -> Arrays.asList((t.split(" ")));
		Function<String, Integer> splitAndCountThen = split.andThen(List::size);
		return splitAndCountThen.apply(string);
	}

	/**
	 * 4.b используйте compose
	 */
	public int getCountFromStringCompose(String string) {
		Function<List<String>, Integer> count = List::size;
		Function<String, Integer> splitAndCountCompose = count.compose(t -> Arrays.asList((t.split(" "))));
		return splitAndCountCompose.apply(string);
	}

	public class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			setName(name);
			setAge(age);
		}

		public Person(String name) {
			this(name, 0);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	/**
	 * 5. Напишите функцию create, принимающую в качестве аргумента строку и
	 * возвращающую Person с именем равным переданной строке. Перепишите при помощи
	 * method reference.
	 */
	public Person getPersonFromString(String string) {
		Function<String, Person> create = Person::new;
		return create.apply(string);
	}

	/**
	 * 6. Реализуйте функцию max, используя method reference к Math.max. Какой
	 * интерфейс из java.util.function подойдет для функции с двумя параметрами?
	 */
	public double getMax(double left, double right) {
		DoubleBinaryOperator max = Math::max;
		return max.applyAsDouble(left, right);
	}

	/**
	 * 7. Реализуйте функцию getCurrentDate, возвращающую текущую дату () ->
	 * java.util.Date. Какой интерфейс из java.util.function подойдет для функции
	 * без параметров?
	 */
	public Date getCurrentDate() {
		Supplier<Date> getCuttentyDate = Date::new;
		return getCuttentyDate.get();
	}

	/**
	 * 8. Реализуйте функцию isEven (Integer a) -> Boolean. Какой интерфейс из
	 * java.util.function для этого подойдет?
	 */
	public boolean isEven(int number) {
		Predicate<Integer> isEven = num -> (num & 0x01) == 0;
		return isEven.test(number);
	}

	/**
	 * 9. Реализуйте функцию areEqual (Integer a, Integer b) -> Boolean. Какой
	 * интерфейс из java.util.function для этого подойдет?
	 */
	public boolean areEqual(Integer number1, Integer number2) {
		BiPredicate<Integer, Integer> areEqual = Integer::equals;
		return areEqual.test(number1, number2);
	}

	public class Person12a {
		private Person12a father;
		private Person12a mother;

		public Person12a(Person12a father, Person12a mother) {
			setFather(father);
			setMother(mother);
		}

		public Person12a() {
		}

		public Person12a getFather() {
			return father;
		}

		public void setFather(Person12a father) {
			this.father = father;
		}

		public Person12a getMother() {
			return mother;
		}

		public void setMother(Person12a mother) {
			this.mother = mother;
		}
	}

	/**
	 * 12. Необходимо реализовать метод getMothersMotherFather, возвращающего отца
	 * бабушки по материнской линии, двумя способами:
	 * 
	 * 12.a. Реализовать класс Person с двумя полями Person father, Person mother
	 * (задавать их значения в конструкторе). Метод getMothersMotherFather должен
	 * либо вернуть экземпляр класса Person, либо null. Должна быть защита от NPE в
	 * цепочке условий.
	 */
	public Person12a getMothersMotherFather(Person12a person) {
		Person12a result = person.getMother();
		if (result == null) {
			return null;
		}
		result = result.getMother();
		if (result == null) {
			return null;
		}
		result = result.getFather();
		return result;
	}

	public class Person12b {
		private Optional<Person12b> father;
		private Optional<Person12b> mother;

		public Person12b(Person12b father, Person12b mother) {
			setFather(father);
			setMother(mother);
		}

		public Optional<Person12b> getFather() {
			return father;
		}

		public void setFather(Person12b father) {
			this.father = Optional.ofNullable(father);
		}

		public Optional<Person12b> getMother() {
			return mother;
		}

		public void setMother(Person12b mother) {
			this.mother = Optional.ofNullable(mother);
		}
	}

	/**
	 * 12.b. Реализовать класс Person с двумя полями Optional<Person> father,
	 * Optional<Person> mother (передавать в конструктор Person или null,
	 * оборачивать в Optional через Optional#ofNullable). Метод
	 * getMothersMotherFather должен вернуть Optional<Person> и быть реализованным
	 * только на базе вызова цепочки Optional.flatMap
	 */
	public Optional<Person12b> getMothersMotherFather(Person12b person) {
		return Optional.of(person)
				.flatMap(Person12b::getMother)
				.flatMap(Person12b::getMother)
				.flatMap(Person12b::getFather);
	}

	/**
	 * 13. Напишите метод IntStream transform(IntStream stream, IntUnaryOperator
	 * op), трансформирующий каждый элемент при помощи операции op. Выведите
	 * результат на консоль.
	 */
	public IntStream transform(IntStream stream, IntUnaryOperator op) {
		return stream.map(op);
	}

	/**
	 * 14. Задача аналогичная предыдущей, только теперь нужно трансформировать
	 * входящий Stream в параллельный, обратите внимание на изменившийся вывод на
	 * консоль.
	 */
	public IntStream transformParallel(IntStream stream, IntUnaryOperator op) {
		return stream.parallel().map(op);
	}

	/**
	 * 15. Реализуйте класс Person(String name, int age). Имея список List<Person>,
	 * при помощи Stream API необходимо вернуть уникальные имена для всех людей
	 * старше 30 лет, отсортированные по длине имени.
	 */
	public List<String> getPersonByAge(List<Person> persons) {
		return persons.stream()
				.filter(a -> a.age > 30)
				.map(Person::getName)
				.distinct()
				.sorted(Comparator.comparingInt(String::length))
				.collect(Collectors.toList());
	}

	/**
	 * 16. Имея список List<Person>, при помощи Stream API необходимо вернуть
	 * уникальные имена для всех людей старше 30 лет, отсортированные по количеству
	 * людей с одинаковым именем. Используйте Collectors.groupingBy
	 */
	public List<String> getPersonByAge2(List<Person> persons) {
		return persons.stream()
				.filter(a -> a.age > 30)
				.collect(Collectors.groupingBy(Person::getName, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(Map.Entry::getValue))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 17. Реализуйте sum(List<Integer> list) и product(List<Integer> list) через Stream.reduce
	 */
	public int sum(List<Integer> list) {
		return list.stream().reduce(0, (x, y) -> x + y);
	}
	public int product (List<Integer> list) {
		return list.stream().reduce(1, (x, y) -> x * y);
	}

}
