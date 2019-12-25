package net.thumbtack.school.database.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class School {

	/**
	 * id для School. Для несохраненной в БД School это поле имеет значение 0, после
	 * сохранения значение присваивается БД
	 */
    private int id = 0;
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
    private List<Group> groups = new ArrayList<>();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return getId() == school.getId() &&
                getYear() == school.getYear() &&
                Objects.equals(getName(), school.getName()) &&
                Objects.equals(getGroups(), school.getGroups());
	}

	@Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear(), getGroups());
	}
}
