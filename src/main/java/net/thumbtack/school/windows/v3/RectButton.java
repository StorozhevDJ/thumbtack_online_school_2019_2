package net.thumbtack.school.windows.v3;

import net.thumbtack.school.windows.v3.base.RectWindow;

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
	 */
	public RectButton(Point topLeft, Point bottomRight, boolean active, String text) {
		setTopLeft(topLeft);
		setBottomRight(bottomRight);
		setActive(active);
		setText(text);
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
	 */
	public RectButton(int xLeft, int yTop, int width, int height, boolean active, String text) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text);
	}

	/**
	 * Создает активную RectButton по координатам углов - левого верхнего и правого
	 * нижнего.
	 *
	 * @param topLeft
	 * @param bottomRight
	 */
	public RectButton(Point topLeft, Point bottomRight, String text) {
		this(topLeft, bottomRight, true, text);
	}

	/**
	 * Создает активную RectButton по координатам левого верхнего угла, ширине и
	 * высоте.
	 *
	 * @param xLeft
	 * @param yTop
	 * @param width
	 * @param height
	 */
	public RectButton(int xLeft, int yTop, int width, int height, String text) {
		this(xLeft, yTop, width, height, true, text);
	}

	/**
	 * Определяет, пересекается ли RectButton с другим RectButton. Считается, что
	 * кнопки пересекаются, если у них есть хоть одна общая точка.
	 *
	 * @param rectButton
	 * @return
	 */
	public boolean isIntersects(RectButton rectButton) {
		if (isInside(rectButton.getBottomRight()) || isInside(rectButton.getTopLeft()))
			return true;
		return rectButton.isInside(getTopLeft()) || rectButton.isInside(getBottomRight());
	}

	/**
	 * Определяет, лежит ли RectButton целиком внутри текущего RectButton.
	 *
	 * @param rectButton
	 * @return
	 */
	public boolean isInside(RectButton rectButton) {
		return isInside(rectButton.getTopLeft()) && isInside(rectButton.getBottomRight());
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
		RectButton other = (RectButton) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
