package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.RoundWindow;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.util.Objects;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RoundButton that = (RoundButton) o;
		return Objects.equals(text, that.text);
    }

    @Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), text);
    }
}
