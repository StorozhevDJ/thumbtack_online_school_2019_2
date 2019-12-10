package net.thumbtack.school.database.model;

public class Subject {

	/**
	 * id для Subject. Для несохраненного в БД Subject это поле имеет значение 0,
	 * после сохранения значение присваивается БД
	 */
	private int id;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Subject other = (Subject) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
