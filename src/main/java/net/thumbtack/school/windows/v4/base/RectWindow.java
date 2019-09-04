package net.thumbtack.school.windows.v4.base;

public abstract class RectWindow extends Window {

    /**
     * Возвращает ширину ListBox.
     *
     * @return
     */
    public int getWidth() {
        return getBottomRight().getX() - getTopLeft().getX() + 1;
    }

    /**
     * Возвращает высоту ListBox.
     *
     * @return
     */
    public int getHeight() {
        return getBottomRight().getY() - getTopLeft().getY() + 1;
    }

    /**
     * Передвигает ListBox так, чтобы левый верхний угол его оказался в точке (x,
     * y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        getBottomRight().moveRel(x - getTopLeft().getX(), y - getTopLeft().getY());
        getTopLeft().moveTo(x, y);
    }

    /**
     * Передвигает ListBox на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        getTopLeft().moveRel(dx, dy);
        getBottomRight().moveRel(dx, dy);
    }

    /**
     * Изменяет ширину и длину ListBox в ratio раз при сохранении координат левой
     * верхней точки. Дробная часть вычисленной длины или ширины отбрасывается. Если
     * при таком изменении длина или ширина какой-то из сторон окажется меньше 1, то
     * она принимается равной 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        int newWigth = (int) (getWidth() * ratio);
        int newHeight = (int) (getHeight() * ratio);
        getBottomRight().moveTo((newWigth >= 1) ? newWigth + getTopLeft().getX() - 1 : getTopLeft().getX(),
                (newHeight >= 1) ? newHeight + getTopLeft().getY() - 1 : getTopLeft().getY());
    }

}
