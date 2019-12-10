package net.thumbtack.school.database.model;

import java.util.ArrayList;
import java.util.List;

public class School {

	/**
	 * id для School. Для несохраненной в БД School это поле имеет значение 0, после
	 * сохранения значение присваивается БД
	 */
	private int id;
	/**
	 * Название школы
	 */
	private String name;
	/**
	 * Год обучения
	 */
	private int year;
	/**
	 * Список групп
	 */
	private List<Group> groups;

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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * Конструктор без параметров с пустым телом. На этом занятии он нам не
	 * понадобится, но будет нужен на следующем занятии, поэтому лучше его сразу
	 * сделать.
	 */
	public School() {

	}

	/**
	 * Конструктор, устанавливающий значения всех полей
	 * 
	 * @param id
	 * @param name
	 * @param year
	 * @param groups
	 */
	public School(int id, String name, int year, List<Group> groups) {
		setId(id);
		setName(name);
		setYear(year);
		setGroups(groups);
	}

	/**
	 * Конструктор, устанавливающий значения всех полей. Полю - списку присваивается
	 * пустой список (не null!)
	 * 
	 * @param id
	 * @param name
	 * @param year
	 */
	public School(int id, String name, int year) {
		this(id, name, year, new ArrayList<Group>());
	}

	/**
	 * Конструктор, устанавливающий значения всех полей. Полю id присваивается
	 * значение 0, полю - списку - пустой список (не null!)
	 * 
	 * @param name
	 * @param year
	 */
	public School(String name, int year) {
		this(0, name, year);
	}

	/**
	 * Добавляет Group в School
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		groups.add(group);
	}

	/**
	 * Удаляет Group из School
	 * 
	 * @param group
	 */
	public void removeGroup(Group group) {
		groups.remove(group);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
