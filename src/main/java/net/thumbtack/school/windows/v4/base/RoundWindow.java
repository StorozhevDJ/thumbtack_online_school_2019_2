package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.base.Window;

import java.util.Objects;

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


	/**
	 * Определяет, лежит ли точка (x, y) внутри RoundButton. Если точка лежит на
	 * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
	 * пиксельной структурой изображения и рассматриваем RoundButton как
	 * математический круг.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInside(int x, int y) {

		/*
		 * x^2+y^2=r^2 (for circle with center in 0) (x-a)^2 + (y-b)^2 = r^2 (for circle
		 * with center with x=a, y=b)
		 *
		 */
		int dx = x - getCenter().getX();
		int dy = y - getCenter().getY();
		return (dx * dx + dy * dy) <= getRadius() * getRadius();
	}

	/**
	 * Определяет, лежит ли точка point внутри RoundButton. Если точка лежит на
	 * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
	 * пиксельной структурой изображения и рассматриваем RoundButton как
	 * математический круг.
	 *
	 * @param point
	 * @return
	 */
	public boolean isInside(Point point) {
		return isInside(point.getX(), point.getY());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RoundWindow that = (RoundWindow) o;
		return radius == that.radius &&
				Objects.equals(center, that.center);
	}

    @Override
    public int hashCode() {
		return Objects.hash(super.hashCode(), center, radius);
    }
}
