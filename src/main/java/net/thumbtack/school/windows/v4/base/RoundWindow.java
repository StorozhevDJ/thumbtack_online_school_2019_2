package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.base.Window;

public abstract class RoundWindow extends Window {

    private Point center;
    private int radius;

    /**
     * Устанавливает центр RoundButton.
     *
     * @param x
     * @param y
     */
    public void setCenter(int x, int y) {
        center = new Point(x, y);
    }

    /**
     * Возвращает центр RoundButton.
     *
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Возвращает радиус RoundButton.
     *
     * @return
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Устанавливает радиус RoundButton.
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Передвигает RoundButton так, чтобы центр его оказался в точке (x, y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        getCenter().moveTo(x, y);
    }

    /**
     * Передвигает RoundButton на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        getCenter().moveRel(dx, dy);
    }

    /**
     * Изменяет радиус RoundButton в ratio раз, не изменяя центра. Дробная часть
     * вычисленного таким образом радиуса отбрасывается. Если вычисленный радиус
     * окажется меньше 1, то он принимается равным 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        setRadius((int) (getRadius() * ratio));
        if (getRadius() < 1) {
            setRadius(1);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((center == null) ? 0 : center.hashCode());
        result = prime * result + radius;
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
        RoundWindow other = (RoundWindow) obj;
        if (center == null) {
            if (other.center != null)
                return false;
        } else if (!center.equals(other.center))
            return false;
        if (radius != other.radius)
            return false;
        return true;
    }

}
