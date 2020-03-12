package com.lineate.function1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import org.junit.Test;

import com.lineate.function.Function1;
import com.lineate.function.Function1.Person12b;
import com.lineate.function.MyFunction;

public class TestFunction1 {

	@Test
	public void Test1() {
		assertEquals(3, new Function1().splitStringToList("Петя Васечкин молодец").size());
	}

	@Test
	public void Test3() {
		assertEquals(3, new Function1().getCountFromStringList(Arrays.asList("Петя", "Васечкин", "молодец")));
	}

	@Test
	public void Test4() {
		assertEquals(3, new Function1().getCountFromStringAndThen("Петя Васечкин молодец"));
	}

	@Test
	public void Test5() {
		assertEquals(3, new Function1().getCountFromStringCompose("Петя Васечкин молодец"));
	}

	@Test
	public void Test6() {
		Function1.Person person = new Function1().getPersonFromString("Петя");
		assertEquals("Петя", person.getName());
	}

	@Test
	public void Test7() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String expectedDate = formatter.format(new Function1().getCurrentDate());
		String dateToTest = formatter.format(new Date());
		assertEquals(expectedDate, dateToTest);
	}

	@Test
	public void Test8() {
		Function1 f = new Function1();
		assertEquals(true, f.isEven(1824));
		assertEquals(false, f.isEven(1571));
	}

	@Test
	public void Test9() {
		Function1 f = new Function1();
		assertEquals(true, f.areEqual(1824, 1824));
		assertEquals(false, f.areEqual(1571, 1824));
	}

	@Test
	public void Test10() {
		MyFunction<Integer, String> length = String::length;
		assertTrue(6 == length.apply("string"));
	}

	@Test
	public void Test12a() {
		Function1 f = new Function1();
		Function1.Person12a person = f
				.getMothersMotherFather(f.new Person12a(f.new Person12a(), f.new Person12a(f.new Person12a(),
						f.new Person12a(f.new Person12a(f.new Person12a(), f.new Person12a()), f.new Person12a()))));
		assertNotNull(person.getFather());

		person = f.getMothersMotherFather(f.new Person12a(f.new Person12a(), f.new Person12a()));
		assertNull(person);
	}

	@Test
	public void Test12b() {
		Function1 f = new Function1();
		Optional<Person12b> person = f.getMothersMotherFather(f.new Person12b(f.new Person12b(null, null),
				f.new Person12b(f.new Person12b(null, null),
						f.new Person12b(f.new Person12b(f.new Person12b(null, null), f.new Person12b(null, null)),
								f.new Person12b(null, null)))));
		assertTrue(person.isPresent());

		person = f.getMothersMotherFather(f.new Person12b(f.new Person12b(null, null), f.new Person12b(null, null)));
		assertFalse(person.isPresent());
	}

	@Test
	public void Test13() {
		IntUnaryOperator op = a -> 2 * a;
		new Function1().transform(IntStream.of(120, 410, 85, 32, 314, 12), op).forEach(System.out::println);
	}

	@Test
	public void Test14() {
		new Function1().transformParallel(IntStream.of(120, 410, 85, 32, 314, 12), a -> 2 * a).forEach(System.out::println);
	}

	@Test
	public void Test15() {
		Function1 f = new Function1();
		
		@SuppressWarnings("serial")
		List<Function1.Person> list = new ArrayList<Function1.Person>() {{
		    add(f.new Person("Ira", 5));
		    add(f.new Person("Vova", 15));
		    add(f.new Person("Yura", 25));
		    add(f.new Person("Вася", 35));
		    add(f.new Person("Вася", 45));
		    add(f.new Person("Ira", 55));
		}};
		List<String> nameList = f.getPersonByAge(list);
		
		assertEquals(2, nameList.size());
		assertTrue(nameList.contains("Ira"));
		assertTrue(nameList.contains("Вася"));
	}
	
	@Test
	public void Test16() {
		Function1 f = new Function1();
		
		@SuppressWarnings("serial")
		List<Function1.Person> list = new ArrayList<Function1.Person>() {{
		    add(f.new Person("Ira", 25));
		    add(f.new Person("Vova", 35));
		    add(f.new Person("Yura", 45));
		    add(f.new Person("Вася", 55));
		    add(f.new Person("Вася", 65));
		    add(f.new Person("Ira", 75));
		}};
		List<String> nameList = f.getPersonByAge2(list);
		
		assertEquals(4, nameList.size());
		assertTrue(nameList.contains("Ira"));
		assertTrue(nameList.contains("Vova"));
		assertTrue(nameList.contains("Yura"));
		assertEquals("Вася", nameList.get(3));
	}
	
	@Test
	public void Test17() {
		Function1 f = new Function1();
		assertTrue(f.sum(Arrays.asList(0, 1, 2, 3, 4)) == 10);
		assertTrue(f.product(Arrays.asList(1, 2, 3, 4, 5)) == 120);
	}
}