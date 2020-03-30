package com.lineate.threads.ttschool;

public class TrainingException extends Exception {

    private static final long serialVersionUID = -3903342712408134702L;

    private TrainingErrorCode trainingErrorCode;

    public TrainingException(TrainingErrorCode trainingErrorCode) {
        this.trainingErrorCode = trainingErrorCode;
    }

    public TrainingErrorCode getErrorCode() {
        return trainingErrorCode;
    }

}
