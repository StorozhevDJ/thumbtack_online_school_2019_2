package net.thumbtack.school.windows.v2;

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
	 */
	public RectButton3D(Point topLeft, Point bottomRight, boolean active, String text, int zHeight) {
		super(topLeft, bottomRight, active, text);
		setZHeight(zHeight);
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
	 */
	public RectButton3D(int xLeft, int yTop, int width, int height, boolean active, String text, int zHeight) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text, zHeight);
	}

	/**
	 * Создает активную RectButton3D по координатам углов - левого верхнего и
	 * правого нижнего, строке на кнопке text и высоте zHeight.
	 *
	 * @param topLeft
	 * @param bottomRight
	 * @param text
	 * @param zHeight
	 */
	public RectButton3D(Point topLeft, Point bottomRight, String text, int zHeight) {
		this(topLeft, bottomRight, true, text, zHeight);
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
	 */
	public RectButton3D(int xLeft, int yTop, int width, int height, String text, int zHeight) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, text, zHeight);
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + zHeight;
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
		RectButton3D other = (RectButton3D) obj;
		return zHeight == other.zHeight;
	}

	/**
	 * методы equals и hashCode.
	 */

}
