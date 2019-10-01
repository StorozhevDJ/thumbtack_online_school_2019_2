package net.thumbtack.school.ttschool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        groups = new HashSet<>();
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
        if ((name == null) || (name.equals(""))) {
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
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
        if (containsGroup(group)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        }
        groups.add(group);
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
        List<Group> group = groups.stream().filter(g -> g.getName().equals(name)).collect(Collectors.toList());
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
        return !groups.stream().filter(g -> g.getName().equals(group.getName())).collect(Collectors.toSet()).isEmpty();
    }

    /**
     * Методы equals и hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
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
