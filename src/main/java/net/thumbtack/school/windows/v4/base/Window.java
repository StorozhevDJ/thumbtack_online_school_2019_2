package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.iface.Movable;
import net.thumbtack.school.windows.v4.iface.Resizable;

public abstract class Window implements Movable, Resizable {

    private Point topLeft, bottomRight;
    private WindowState active;

    /**
     * Возвращает левую верхнюю точку ListBox.
     *
     * @return
     */
    public Point getTopLeft() {
        return topLeft;
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
     * Устанавливает левую верхнюю точку ListBox.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
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
     * Возвращает true, если кнопка активна, иначе false.
     *
     * @return
     */
    public WindowState getState() {
        return active;
    }

    /**
     * Устанавливает состояние активности RectButton.
     *
     * @param active
     * @throws WindowException
     */
    public void setState(WindowState active) throws WindowException {
        if ((this.active == WindowState.DESTROYED) && (active != WindowState.DESTROYED) || active == null) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        this.active = active;
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

    public abstract void moveTo(int i, int i1);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + ((bottomRight == null) ? 0 : bottomRight.hashCode());
        result = prime * result + ((topLeft == null) ? 0 : topLeft.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Window other = (Window) obj;
        if (active != other.active)
            return false;
        if (bottomRight == null) {
            if (other.bottomRight != null)
                return false;
        } else if (!bottomRight.equals(other.bottomRight))
            return false;
        if (topLeft == null) {
            if (other.topLeft != null)
                return false;
        } else if (!topLeft.equals(other.topLeft))
            return false;
        return true;
    }

}
