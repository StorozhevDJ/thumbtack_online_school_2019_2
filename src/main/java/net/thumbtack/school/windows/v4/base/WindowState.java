package net.thumbtack.school.windows.v4.base;

public enum WindowState {
    ACTIVE, INACTIVE, DESTROYED;

    /**
     * Возвращает WindowState по переданной текстовой строке
     *
     * @param stateString
     * @return
     */
    public static WindowState fromString(String stateString) throws WindowException {
        if (stateString == null) throw new WindowException(WindowErrorCode.WRONG_STATE);
        try {
            return WindowState.valueOf(stateString);
        } catch (IllegalArgumentException e) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
    }
}
