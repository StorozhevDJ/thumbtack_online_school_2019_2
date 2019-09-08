package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;

public class Manager<T extends Window> {

    private T win;

    public Manager(T win) throws WindowException {
        super();
        setWindow(win);
    }

    public T getWindow() {
        return win;
    }

    public void setWindow(T win) throws WindowException {
        if (win == null) throw new WindowException(WindowErrorCode.NULL_WINDOW);
        this.win = win;
    }

    /**
     * Передвигает окна, находящееся под управлением менеджера, так, чтобы его
     * базовая точка (левый верхний угол или центр соответственно) его оказалась в
     * точке (x,y)
     *
     * @return
     */
    public void moveTo(int x, int y) {
        win.moveTo(x, y);
    }

    /**
     * Передвигает окно так, так, чтобы его базовая точка (левый верхний угол или
     * центр соответственно) его оказалась в точке point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        win.moveTo(point);
    }

}
