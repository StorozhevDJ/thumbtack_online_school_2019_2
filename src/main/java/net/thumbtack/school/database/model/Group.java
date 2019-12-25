package net.thumbtack.school.database.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
	/**
	 * id для Group. Для несохраненной в БД Group это поле имеет значение 0, после
	 * сохранения значение присваивается БД
	 */
	private int id = 0;
	/**
	 * Название группы
	 */
	private String name;
	/**
	 * Номер аудитории
	 */
	private String room;
	/**
	 * Список учащихся
	 */
	private List<Trainee> trainees = new ArrayList<>();
	/**
	 * Список предметов
	 */
	private List<Subject> subjects = new ArrayList<>();

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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public List<Trainee> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<Trainee> trainees) {
		this.trainees = trainees;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	/**
	 * Конструктор без параметров с пустым телом. На этом занятии он нам не
	 * понадобится, но будет нужен на следующем занятии, поэтому лучше его сразу
	 * сделать.
	 */
	public Group() {

	}

	/**
	 * Конструктор, устанавливающий значения всех полей
	 * 
	 * @param id
	 * @param name
	 * @param room
	 * @param trainees
	 * @param subjects
	 */
	public Group(int id, String name, String room, List<Trainee> trainees, List<Subject> subjects) {
		setId(id);
		setName(name);
		setRoom(room);
		setTrainees(trainees);
		setSubjects(subjects);
	}

	/**
	 * Конструктор, устанавливающий значения всех полей. Полям - спискам
	 * присваивается пустой список (не null!)
	 * 
	 * @param id
	 * @param name
	 * @param room
	 */
	public Group(int id, String name, String room) {
		this(id, name, room, new ArrayList<Trainee>(), new ArrayList<Subject>());
	}

	/**
	 * Конструктор, устанавливающий значения всех полей. Полю id присваивается
	 * значение 0, полям - спискам - пустые списки (не null!)
	 * 
	 * @param name
	 * @param room
	 */
	public Group(String name, String room) {
		this(0, name, room);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Group)) return false;
		Group group = (Group) o;
		return getId() == group.getId() &&
				Objects.equals(getName(), group.getName()) &&
				Objects.equals(getRoom(), group.getRoom()) &&
				Objects.equals(getTrainees(), group.getTrainees()) &&
				Objects.equals(getSubjects(), group.getSubjects());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getRoom(), getTrainees(), getSubjects());
	}

	/**
	 * Добавляет Trainee в Group
	 * 
	 * @param trainee
	 */
	public void addTrainee(Trainee trainee) {
		trainees.add(trainee);
	}

	/**
	 * Удаляет Trainee из Group
	 * 
	 * @param trainee
	 */
	public void removeTrainee(Trainee trainee) {
		trainees.remove(trainee);
	}

	/**
	 * Добавляет Subject в Group
	 * 
	 * @param subject
	 */
	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	/**
	 * Удаляет Subject из Group
	 * 
	 * @param subject
	 */
	public void removeSubject(Subject subject) {
		subjects.remove(subject);
	}

}
