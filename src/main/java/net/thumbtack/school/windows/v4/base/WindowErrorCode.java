package net.thumbtack.school.windows.v4.base;

public enum WindowErrorCode {
    WRONG_STATE("Не корректное состояние окна"), // При создании окна передается WindowState.DESTROYED или null. При
    // изменении состояния состояние устанавливается в null или окно,
    // находящееся в WindowState.DESTROYED, переводится в иное
    // состояние.
    WRONG_INDEX("Передан недопустимый индекс для массива строк"), // Передан недопустимый индекс для массива строк.
    EMPTY_ARRAY("Массив строк равен null"); // Массив строк равен null.

    private String errorString;

    WindowErrorCode(String text) {
        this.errorString = text;
    }

    public String getText() {
        return this.errorString;
    }

    public static WindowErrorCode fromString(String text) {
        for (WindowErrorCode b : WindowErrorCode.values()) {
            if (b.errorString.equals(text)) {
                return b;
            }
        }
        return null;
    }
}
