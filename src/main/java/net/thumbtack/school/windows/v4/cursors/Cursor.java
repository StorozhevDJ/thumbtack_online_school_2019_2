package net.thumbtack.school.windows.v4.cursors;

import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.iface.Movable;

public class Cursor implements Movable {

    private int x, y;

    private CursorForm cursorForm;

    /**
     * Создает курсор указанной формы. Мы пока не будем конкретизировать понятие
     * вида курсора, а просто будем считать, что имеются различные формы курсоров,
     * каждая форма имеет свой номер, нумерация произвольная. Курсор помещается в
     * точку (x,y).
     *
     * @param x
     * @param y
     * @param cursorForm
     */
    public Cursor(int x, int y, CursorForm cursorForm) {
        setX(x);
        setY(y);
        setCursorForm(cursorForm);
    }

    /**
     * Создает курсор указанной формы. Курсор помещается в точку point.
     *
     * @param point
     * @param cursorForm
     */
    public Cursor(Point point, CursorForm cursorForm) {
        this(point.getX(), point.getY(), cursorForm);
    }

    /**
     * Возвращает форму курсора.
     *
     * @return
     */
    public CursorForm getCursorForm() {
        return cursorForm;
    }

    /**
     * Устанавливает форму курсора.
     *
     * @param cursorForm
     */
    public void setCursorForm(CursorForm cursorForm) {
        this.cursorForm = cursorForm;
    }

    /**
     * Возвращает x-координату курсора.
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает y-координату курсора.
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Устанавливает x-координату курсора.
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Устанавливает y-координату курсора.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Перемещает курсор в точку (x,y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Перемещает курсор в точку point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        moveTo(point.getX(), point.getY());
    }

    /**
     * Перемещает курсор на (dx,dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

}
