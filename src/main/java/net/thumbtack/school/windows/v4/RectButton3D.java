package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

import java.util.Objects;

public class RectButton3D extends RectButton {

    private int zHeight;

    /**
     * Создает RectButton3D по координатам углов - левого верхнего и правого
     * нижнего, высоте zHeight, строке на кнопке text и флагу активности. Обращаем
     * внимание на то, что обе точки входят в кнопку, так что если создать кнопку с
     * topLeft.equals(bottomRight), то будет создана кнопка ширины и высоты 1.
     *
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(Point topLeft, Point bottomRight, WindowState active, String text, int zHeight)
            throws WindowException {
        super(topLeft, bottomRight, active, text);
        setZHeight(zHeight);
    }

    /**
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(Point topLeft, Point bottomRight, String active, String text, int zHeight)
            throws WindowException {
        this(topLeft, bottomRight, WindowState.fromString(active), text, zHeight);
    }

    /**
     * Создает RectButton3D по координатам левого верхнего угла, ширине, высоте,
     * строке на кнопке text, высоте zHeight и флагу активности.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(int xLeft, int yTop, int width, int height, WindowState active, String text, int zHeight)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text, zHeight);
    }

    /**
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(int xLeft, int yTop, int width, int height, String active, String text, int zHeight)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.fromString(active),
                text, zHeight);
    }

    /**
     * Создает активную RectButton3D по координатам углов - левого верхнего и
     * правого нижнего, строке на кнопке text и высоте zHeight.
     *
     * @param topLeft
     * @param bottomRight
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(Point topLeft, Point bottomRight, String text, int zHeight) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, text, zHeight);
    }

    /**
     * Создает активную RectButton3D по координатам левого верхнего угла, ширине,
     * строке на кнопке text и высоте zHeight.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param text
     * @param zHeight
     * @throws WindowException
     */
    public RectButton3D(int xLeft, int yTop, int width, int height, String text, int zHeight) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.ACTIVE, text,
                zHeight);
    }

    /**
     * Возвращает высоту RectButton3D по оси Z.
     *
     * @return
     */
    public int getZHeight() {
        return zHeight;
    }

    /**
     * Устанавливает высоту RectButton3D по оси Z.
     *
     * @param zHeight
     */
    public void setZHeight(int zHeight) {
        this.zHeight = zHeight;
    }

    /**
     * Определяет, лежит ли RectButton3D целиком внутри текущего RectButton3D.
     * Высота учитывается.
     *
     * @param rectButton3D
     * @return
     */
    public boolean isInside(RectButton3D rectButton3D) {
        return (super.isInside(rectButton3D) && (zHeight >= rectButton3D.getZHeight()));
    }


    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RectButton3D that = (RectButton3D) o;
		return zHeight == that.zHeight;
    }

    @Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), zHeight);
    }
}
