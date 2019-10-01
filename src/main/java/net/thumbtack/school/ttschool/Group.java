package net.thumbtack.school.ttschool;

import java.util.*;
import java.util.stream.Collectors;

public class Group {

    private String name, room;
    private List<Trainee> studentList;

    /**
     * Создает Group с указанными значениями полей и пустым списком студентов. Для
     * недопустимых значений входных параметров выбрасывает TrainingException с
     * соответствующим TrainingErrorCode
     *
     * @param name
     * @param room
     */
    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        studentList = new ArrayList<Trainee>();
    }

    /**
     * Возвращает имя группы
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя группы. Для недопустимого значения входного параметра
     * выбрасывает TrainingException с TrainingErrorCode.GROUP_WRONG_NAME (здесь и
     * далее добавляйте новые коды ошибок в TrainingErrorCode)
     *
     * @param name
     */
    public void setName(String name) throws TrainingException {
        if ((name == null) || (name.equals(""))) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    /**
     * Возвращает название аудитории
     *
     * @return
     */
    public String getRoom() {
        return room;
    }

    /**
     * Устанавливает название аудитории. Для недопустимого значения входного
     * параметра выбрасывает TrainingException с TrainingErrorCode.GROUP_WRONG_ROOM
     *
     * @param room
     */
    public void setRoom(String room) throws TrainingException {
        if ((room == null) || (room == "")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    /**
     * Возвращает список учащихся.
     *
     * @return
     */
    public List<Trainee> getTrainees() {
        return studentList;
    }

    /**
     * Добавляет Trainee в группу.
     *
     * @param trainee
     */
    public void addTrainee(Trainee trainee) {
        studentList.add(trainee);
    }

    /**
     * Удаляет Trainee из группы. Если такого Trainee в группе нет, выбрасывает
     * TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND
     *
     * @param trainee
     * @throws TrainingException
     */
    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!studentList.remove(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    /**
     * Удаляет Trainee с данным номером в списке из группы. Если номер не является
     * допустимым, выбрасывает TrainingException с
     * TrainingErrorCode.TRAINEE_NOT_FOUND
     *
     * @param index
     */
    public void removeTrainee(int index) throws TrainingException {
        if (studentList.size() > index) {
            studentList.remove(index);
        } else {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    /**
     * Возвращает первый найденный в списке группы Trainee, у которого имя равно
     * firstName. Если такого Trainee в группе нет, выбрасывает TrainingException с
     * TrainingErrorCode.TRAINEE_NOT_FOUND
     *
     * @param firstName
     * @return
     */
    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee trainee : studentList) {
            if (trainee.getFirstName() == firstName) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    /**
     * Возвращает первый найденный в списке группы Trainee, у которого полное имя
     * равно fullName. Если такого Trainee в группе нет, выбрасывает
     * TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND
     *
     * @param fullName
     * @return
     */
    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        for (Trainee trainee : studentList) {
            if (fullName.equals(trainee.getFullName())) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    /**
     * Сортирует список Trainee группы, упорядочивая его по возрастанию имени
     * Trainee.
     */
    public void sortTraineeListByFirstNameAscendant() {
        Collections.sort(studentList, Comparator.comparing(Trainee::getFirstName));
    }

    /**
     * Сортирует список Trainee группы, упорядочивая его по убыванию оценки Trainee.
     */
    public void sortTraineeListByRatingDescendant() {
        Collections.sort(studentList, Comparator.comparing(Trainee::getRating).reversed());
    }

    /**
     * Переворачивает список Trainee группы, то есть последний элемент списка
     * становится начальным, предпоследний - следующим за начальным и т.д..
     */
    public void reverseTraineeList() {
        Collections.reverse(studentList);
    }

    /**
     * Циклически сдвигает список Trainee группы на указанное число позиций. Для
     * положительного значения positions сдвигает вправо, для отрицательного - влево
     * на модуль значения positions.
     *
     * @param positions
     */
    public void rotateTraineeList(int positions) {
        Collections.rotate(studentList, positions);
    }

    /**
     * Возвращает список тех Trainee группы , которые имеют наивысшую оценку. Иными
     * словами, если в группе есть Trainee с оценкой 5, возвращает список получивших
     * оценку 5, если же таких нет, но есть Trainee с оценкой 4, возвращает список
     * получивших оценку 4 и т.д. Для пустого списка выбрасывает TrainingException с
     * TrainingErrorCode.TRAINEE_NOT_FOUND. Желательно сделать этот метод без
     * сортировки и в один проход по списку.
     *
     * @return
     */
    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        OptionalInt maxRating = studentList.stream().mapToInt(i -> i.getRating()).max();
        if (!maxRating.isPresent()) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return studentList.stream().filter(item -> item.getRating() == maxRating.getAsInt()).collect(Collectors.toList());
    }

    /**
     * Проверяет, есть ли в группе хотя бы одна пара Trainee, для которых совпадают
     * имя, фамилия и оценка.
     *
     * @return
     */
    public boolean hasDuplicates() {
        return studentList.stream().distinct().count() != studentList.size();
    }

    /**
     * Методы equals и hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((studentList == null) ? 0 : studentList.hashCode());
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
        Group other = (Group) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (studentList == null) {
            if (other.studentList != null)
                return false;
        } else if (!studentList.equals(other.studentList))
            return false;
        return true;
    }
}
