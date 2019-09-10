package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.RectWindow;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.util.Objects;

public class RectButton extends RectWindow {

    private String text;

    /**
     * Создает RectButton по координатам углов - левого верхнего и правого нижнего,
     * и флагу активности. Обращаем внимание на то, что обе точки входят в кнопку,
     * так что если создать кнопку с topLeft.equals(bottomRight), то будет создана
     * кнопка ширины и высоты 1.
     *
     * @param topLeft
     * @param bottomRight
     * @param active
     * @throws WindowException
     */
    public RectButton(Point topLeft, Point bottomRight, WindowState active, String text) throws WindowException {
        if ((active == WindowState.DESTROYED) || (active == null)) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        setTopLeft(topLeft);
        setBottomRight(bottomRight);
        setState(active);
        setText(text);
    }

    /**
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param text
     * @throws WindowException
     */
    public RectButton(Point topLeft, Point bottomRight, String active, String text) throws WindowException {
        this(topLeft, bottomRight, WindowState.fromString(active), text);
    }

    /**
     * Создает RectButton по координатам левого верхнего угла, ширине, высоте и
     * флагу активности.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @throws WindowException
     */
    public RectButton(int xLeft, int yTop, int width, int height, WindowState active, String text)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text);
    }

    /**
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param text
     * @throws WindowException
     */
    public RectButton(int xLeft, int yTop, int width, int height, String active, String text) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.fromString(active),
                text);
    }

    /**
     * Создает активную RectButton по координатам углов - левого верхнего и правого
     * нижнего.
     *
     * @param topLeft
     * @param bottomRight
     * @throws WindowException
     */
    public RectButton(Point topLeft, Point bottomRight, String text) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, text);
    }

    /**
     * Создает активную RectButton по координатам левого верхнего угла, ширине и
     * высоте.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @throws WindowException
     */
    public RectButton(int xLeft, int yTop, int width, int height, String text) throws WindowException {
        this(xLeft, yTop, width, height, WindowState.ACTIVE, text);
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
        RectButton that = (RectButton) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

}
