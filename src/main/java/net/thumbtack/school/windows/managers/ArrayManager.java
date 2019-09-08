package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.cursors.Cursor;

public class ArrayManager<T extends Window> {

    private T[] win;

    public ArrayManager(T[] win) throws WindowException {
        super();
        setWindows(win);
    }

    public T[] getWindows() {
        return win;
    }

    public void setWindows(T[] windows) throws WindowException {
        for (T window : windows) {
            if (window == null) {
                throw new WindowException(WindowErrorCode.NULL_WINDOW);
            }
        }
        this.win = windows;
    }

    /**
     * возвращающий i-й элемент вложенного массива
     */
    public T getWindow(int i) {
        return win[i];
    }

    /**
     * устанавливающий i-й элемент вложенного массива
     */
    public void setWindow(T window, int i) {
        win[i] = window;
    }

    /**
     * метод isSameSize, проверяющий, равен ли размер вложенного массива размеру
     * вложенного массива другого ArrayManager
     *
     * @param win
     * @return
     */
    boolean isSameSize(ArrayManager<? extends Window> win) {
        return getWindows().length == win.getWindows().length;
    }

    /**
     * Определяет, лежат ли все окна, находящиеся под контролем менеджера, в
     * пределах некоторого Desktop.
     */
    public boolean allWindowsFullyVisibleOnDesktop(Desktop desktop) {
        for (T window : win) {
            if (!window.isFullyVisibleOnDesktop(desktop)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Определяет, лежит ли хоть одно окно из находящихся под контролем менеджера, в
     * пределах некоторого Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean anyWindowFullyVisibleOnDesktop(Desktop desktop) {
        for (T window : win) {
            if (window.isFullyVisibleOnDesktop(desktop)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает первое окно в списке менеджера, в которое попадает некоторый
     * Cursor. Считается, что курсор попадает в окно, если его координаты находятся
     * в пределах окна. Если такого окна нет, возвращает null.
     *
     * @param cursor
     * @return
     */
    public Window getFirstWindowFromCursor(Cursor cursor) {
        for (T window : win) {
            if (window.isInside(cursor.getX(), cursor.getY())) return window;
        }
        return null;
    }

}
