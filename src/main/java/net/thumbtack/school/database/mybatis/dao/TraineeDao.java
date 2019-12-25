package net.thumbtack.school.database.mybatis.dao;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;

import java.util.List;

public interface TraineeDao {

    /**
     * Вставляет Trainee в базу данных.
     * Если group не null, Trainee после вставки принадлежит этой группе
     * в противном случае Trainee не принадлежит никакой группе
     *
     * @param group
     * @param trainee
     * @return
     */
    Trainee insert(Group group, Trainee trainee);

    /**
     * Получает Trainee по его ID.
     *
     * @param id
     * @return Если такого ID нет, метод возвращает null
     */
    Trainee getById(int id);

    /**
     * Получает список всех Trainee, независимо от их Group.
     *
     * @return Если БД не содержит ни одного Trainee, метод возвращает пустой список
     */
    List<Trainee> getAll();

    /**
     * Изменяет Trainee в базе данных. Метод не изменяет принадлежности Trainee к Group
     *
     * @param trainee
     * @return
     */
    void update(Trainee trainee);

    /**
     * Получает список всех Trainee при условиях
     *
     * @param firstName если firstName не равен null - только имеющих это имя
     * @param lastName  если lastName не равен null - только имеющих эту фамилию
     * @param rating    если rating не равен null - только имеющих эту оценку
     * @return
     */
    List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating);

    /**
     * Вставляет список из Trainee в базу данных.
     * Вставленные Trainee не принадлежат никакой группе
     *
     * @param trainees
     */
    void batchInsert(List<Trainee> trainees);

    /**
     * Удаляет Trainee из базы данных
     *
     * @param trainee
     */
    void delete(Trainee trainee);

    /**
     * Удаляет все Trainee из базы данных
     */
    void deleteAll();


}
