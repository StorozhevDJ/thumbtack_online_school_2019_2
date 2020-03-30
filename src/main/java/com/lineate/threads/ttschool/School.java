package com.lineate.threads.ttschool;

import java.util.*;
import java.util.stream.Collectors;

public class School {
    private String name;
    private int year;
    private Set<Group> groups;

    /**
     * Создает School с указанными значениями полей и пустым множеством групп. Для
     * недопустимых значений входных параметров выбрасывает TrainingException с
     * соответствующим TrainingErrorCode
     *
     * @return
     * @throws TrainingException
     */
    public School(String name, int year) throws TrainingException {
        setName(name);
        setYear(year);
        groups = Collections.synchronizedSet(new HashSet<>());
    }

    /**
     * Возвращает название школы.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название школы. Для недопустимого значения входного параметра
     * выбрасывает TrainingException с TrainingErrorCode.SCHOOL_WRONG_NAME
     *
     * @param name
     * @throws TrainingException
     */
    public void setName(String name) throws TrainingException {
        synchronized (name) {
            if ((name == null) || (name.equals(""))) {
                throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
            }
            this.name = name;
        }
    }

    /**
     * Возвращает год начала обучения.
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Устанавливает год начала обучения.
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Возвращает список групп
     *
     * @return
     */
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * Добавляет Group в школу. Если группа с таким именем уже есть, выбрасывает
     * TrainingException с TrainingErrorCode.DUPLICATE_GROUP_NAME
     *
     * @param group
     * @throws TrainingException
     */
    public void addGroup(Group group) throws TrainingException {
        synchronized (group) {
            if (containsGroup(group)) {
                throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
            }
            groups.add(group);
        }
    }

    /**
     * Удаляет Group из школы. Если такой Group в школе нет, выбрасывает
     * TrainingException с TrainingErrorCode.GROUP_NOT_FOUND
     *
     * @param group
     * @throws TrainingException
     */
    public void removeGroup(Group group) throws TrainingException {
        if (!groups.remove(group)) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    /**
     * Удаляет Group с данным названием из школы. Если группа с таким названием не
     * найдена, выбрасывает TrainingException с TrainingErrorCode.GROUP_NOT_FOUND
     *
     * @param name
     * @throws TrainingException
     */
    public void removeGroup(String name) throws TrainingException {
        List<Group> group;
        synchronized (groups) {
            group = groups.stream().filter(g -> g.getName().equals(name)).collect(Collectors.toList());
        }
        if (!group.isEmpty()) {
            removeGroup(group.get(0));
        } else throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
    }

    /**
     * Определяет, есть ли в школе группа с таким названием.
     *
     * @param group
     * @return
     */
    public boolean containsGroup(Group group) {
        synchronized (group) {
            return !groups.stream().filter(g -> g.getName().equals(group.getName())).collect(Collectors.toSet()).isEmpty();
        }
    }

    /**
     * Методы equals и hashCode
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return getYear() == school.getYear() &&
                Objects.equals(getName(), school.getName()) &&
                Objects.equals(getGroups(), school.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getYear(), getGroups());
    }
}
