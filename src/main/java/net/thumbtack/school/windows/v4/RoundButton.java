package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.RoundWindow;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class RoundButton extends RoundWindow {

    private String text;

    /**
     * Создает RoundButton по координатам центра, значению радиуса и флагу
     * активности.
     *
     * @param center
     * @param radius
     * @param active
     * @throws WindowException
     */
    public RoundButton(Point center, int radius, WindowState active, String text) throws WindowException {
        setCenter(center.getX(), center.getY());
        setRadius(radius);
        setState(active);
        setText(text);
    }

    public RoundButton(Point center, int radius, String active, String text) throws WindowException {
        this(center, radius, WindowState.fromString(active), text);
    }

    /**
     * Создает RoundButton по координатам центра, значению радиуса и флагу
     * активности.
     *
     * @param xCenter
     * @param yCenter
     * @param radius
     * @param active
     * @throws WindowException
     */
    public RoundButton(int xCenter, int yCenter, int radius, WindowState active, String text) throws WindowException {
        this(new Point(xCenter, yCenter), radius, active, text);
    }

    public RoundButton(int xCenter, int yCenter, int radius, String active, String text) throws WindowException {
        this(new Point(xCenter, yCenter), radius, active, text);
    }

    /**
     * Создает активную RoundButton по координатам центра и значению радиуса.
     *
     * @param center
     * @param radius
     * @throws WindowException
     */
    public RoundButton(Point center, int radius, String text) throws WindowException {
        this(center, radius, WindowState.ACTIVE, text);
    }

    /**
     * Создает активную RoundButton по координатам центра и значению радиуса.
     *
     * @param xCenter
     * @param yCenter
     * @param radius
     * @throws WindowException
     */
    public RoundButton(int xCenter, int yCenter, int radius, String text) throws WindowException {
        this(new Point(xCenter, yCenter), radius, text);
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

    /**
     * Определяет, верно ли, что вся RoundButton находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        if (((getCenter().getX() - getRadius()) < 0) || ((getCenter().getX() + getRadius()) >= desktop.getWidth())) {
            return false;
        }
        return ((getCenter().getX() - getRadius()) >= 0) && ((getCenter().getX() + getRadius()) < desktop.getWidth());
    }

    /**
     * Возвращает текст окна
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает текст окна
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        RoundButton other = (RoundButton) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

}
