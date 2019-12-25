package net.thumbtack.school.database.model;

import java.util.Objects;

public class Subject {

	/**
	 * id для Subject. Для несохраненного в БД Subject это поле имеет значение 0,
	 * после сохранения значение присваивается БД
	 */
	private int id = 0;
	/**
	 * Название предмета
	 */
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Конструктор без параметров с пустым телом. На этом занятии он нам не
	 * понадобится, но будет нужен на следующем занятии, поэтому лучше его сразу
	 * сделать.
	 */
	public Subject() {

	}

	/**
	 * Конструктор, устанавливающий значения всех полей
	 * 
	 * @param id
	 * @param name
	 */
	public Subject(int id, String name) {
		setId(id);
		setName(name);
	}

	/**
	 * Конструктор, устанавливающий значения всех полей, Полю id присваивается
	 * значение 0.
	 * 
	 * @param name
	 */
	public Subject(String name) {
		this(0, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Subject)) return false;
		Subject subject = (Subject) o;
		return getId() == subject.getId() &&
				Objects.equals(getName(), subject.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
	}
}
