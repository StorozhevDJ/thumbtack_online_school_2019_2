package net.thumbtack.school.windows.v4.base;

public class WindowException extends Exception {

    private static final long serialVersionUID = 1553719719669513176L;
    private WindowErrorCode windowErrorCode;

    public WindowException(WindowErrorCode windowErrorCode) {
        this.windowErrorCode = windowErrorCode;
    }

    public WindowErrorCode getWindowErrorCode() {
        return windowErrorCode;
    }

}
