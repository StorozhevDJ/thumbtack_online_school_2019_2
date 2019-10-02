package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;

import java.util.Objects;

public abstract class RectWindow extends Window {

    private Point topLeft, bottomRight;

    /**
     * Возвращает левую верхнюю точку ListBox.
     *
     * @return
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Устанавливает левую верхнюю точку ListBox.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Возвращает правую нижнюю точку ListBox.
     *
     * @return
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Устанавливает правую нижнюю точку ListBox.
     *
     * @param bottomRight
     */
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

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
     * Определяет, лежит ли точка (x, y) внутри ListBox. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {
        if ((x < getTopLeft().getX()) || (x > getBottomRight().getX())) {
            return false;
        }
        return (y >= getTopLeft().getY()) && (y <= getBottomRight().getY());
    }

    /**
     * Определяет, лежит ли точка point внутри ListBox. Если точка лежит на стороне,
     * считается, что она лежит внутри.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    /**
     * Определяет, верно ли, что весь ListBox находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        if ((getTopLeft().getX() < 0) || (getTopLeft().getY() < 0)) {
            return false;
        }
        return (getBottomRight().getX() <= desktop.getWidth()) && (getBottomRight().getY() <= desktop.getHeight());
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

    /**
     * Определяет, пересекается ли RectWindow с другим RectWindow. Считается, что
     * кнопки пересекаются, если у них есть хоть одна общая точка.
     *
     * @param rect
     * @return
     */
    public boolean isIntersects(RectWindow rect) {
        if (isInside(rect.getBottomRight()) || isInside(rect.getTopLeft())) {
            return true;
        }
        return rect.isInside(getTopLeft()) || rect.isInside(getBottomRight());
    }

    /**
     * Определяет, лежит ли RectWindow целиком внутри текущего RectWindow.
     *
     * @param rect
     * @return
     */
    public boolean isInside(RectWindow rect) {
        return isInside(rect.getTopLeft()) && isInside(rect.getBottomRight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RectWindow that = (RectWindow) o;
        return Objects.equals(topLeft, that.topLeft) &&
                Objects.equals(bottomRight, that.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), topLeft, bottomRight);
    }
}
