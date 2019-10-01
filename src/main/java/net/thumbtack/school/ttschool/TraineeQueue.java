package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.Queue;

public class TraineeQueue {

    private Queue<Trainee> traineeQueue;

    /**
     * Создает TraineeQueue с пустой Queue.
     *
     * @return
     */
    public TraineeQueue() {
        traineeQueue = new LinkedList<Trainee>();
    }

    /**
     * Добавляет студента в очередь.
     *
     * @param trainee
     */
    public void addTrainee(Trainee trainee) {
        traineeQueue.add(trainee);
    }

    /**
     * Удаляет студента из очереди. Метод возвращает удаленного Trainee. Если в
     * очереди никого нет, выбрасывает TrainingException с
     * TrainingErrorCode.EMPTY_TRAINEE_QUEUE.
     *
     * @return
     * @throws TrainingException
     */
    public Trainee removeTrainee() throws TrainingException {
        Trainee trainee = traineeQueue.poll();
        if (trainee == null) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
        return trainee;
    }

    /**
     * Возвращает true, если очередь пуста, иначе false
     *
     * @return
     */
    public boolean isEmpty() {
        return traineeQueue.isEmpty();
    }

}
