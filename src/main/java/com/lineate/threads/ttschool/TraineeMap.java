package com.lineate.threads.ttschool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TraineeMap {

    private Map<Trainee, String> map;

    /**
     * Создает TraineeMap с пустым Map.
     *
     * @return
     */
    public TraineeMap() {
        map = new HashMap<Trainee, String>();
    }

    /**
     * Добавляет пару Trainee - String в Map. Если Map уже содержит информацию об
     * этом Trainee, выбрасывает TrainingException с
     * TrainingErrorCode.DUPLICATE_TRAINEE.
     *
     * @param trainee
     * @param institute
     * @throws TrainingException
     */
    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (map.put(trainee, institute) != null) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
    }

    /**
     * Если в Map уже есть информация о данном Trainee, заменяет пару Trainee -
     * String в Map на новую пару, иначе выбрасывает TrainingException с
     * TrainingErrorCode.TRAINEE_NOT_FOUND.
     *
     * @param trainee
     * @param institute
     * @throws TrainingException
     */
    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (map.replace(trainee, institute) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    /**
     * Удаляет информацию о Trainee из Map. Если Map не содержит информации о таком
     * Trainee, выбрасывает TrainingException с TrainingErrorCode.TRAINEE_NOT_FOUND.
     *
     * @param trainee
     * @throws TrainingException
     */
    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        if (map.remove(trainee) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    /**
     * Возвращает количество элементов в Map, иными словами, количество студентов.
     *
     * @return
     */
    public int getTraineesCount() {
        return map.size();
    }

    /**
     * Возвращает институт, в котором учится данный Trainee. Если Map не содержит
     * информации о таком Trainee, выбрасывает TrainingException с
     * TrainingErrorCode.TRAINEE_NOT_FOUND
     *
     * @param trainee
     * @return
     * @throws TrainingException
     */
    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        String institute = map.get(trainee);
        if (institute == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return institute;
    }

    /**
     * Возвращает Set из всех имеющихся в Map Trainee.
     *
     * @return
     */
    public Set<Trainee> getAllTrainees() {
        return (Set<Trainee>) map.keySet();
    }

    /**
     * Возвращает Set из всех институтов.
     *
     * @return
     */
    public Set<String> getAllInstitutes() {
        return new HashSet<String>(map.values());
    }

    /**
     * Возвращает true, если хоть один студент учится в этом institute, иначе false.
     *
     * @param institute
     * @return
     */
    public boolean isAnyFromInstitute(String institute) {
        return getAllInstitutes().contains(institute);
    }
}
