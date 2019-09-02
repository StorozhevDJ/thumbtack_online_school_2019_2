package net.thumbtack.school.windows.v3;

public class ComboBox extends ListBox {

	private Integer selected;

	/**
	 * Создает ComboBox по координатам углов - левого верхнего и правого нижнего,
	 * флагу активности, набору строк и номеру выделенной строки. Если выделенной
	 * строки нет, в качестве “selected” передается null. Обращаем внимание на то,
	 * что обе точки входят в ComboBox, так что если создать ComboBox с
	 * topLeft.equals(bottomRight), то будет создан ComboBox ширины и высоты 1.
	 * Параметр lines может быть null.
	 * 
	 * @return
	 */
	public ComboBox(Point topLeft, Point bottomRight, boolean active, String[] lines, Integer selected) {
		super(topLeft, bottomRight, active, lines);
		this.selected = selected;
	}

	/**
	 * Создает ComboBox по координатам левого верхнего угла, ширине, высоте, флагу
	 * активности, набору строк и номеру выделенной строки. Если выделенной строки
	 * нет, в качестве “selected” передается null. Параметр lines может быть null.
	 * 
	 * @param xLeft
	 * @param yTop
	 * @param width
	 * @param height
	 * @param active
	 * @param lines
	 * @param selected
	 */
	public ComboBox(int xLeft, int yTop, int width, int height, boolean active, String[] lines, Integer selected) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines, selected);
	}

	/**
	 * Создает активный ComboBox по координатам углов - левого верхнего и правого
	 * нижнего, набору строк и номеру выделенной строки. Если выделенной строки нет,
	 * в качестве “selected” передается null. Параметр lines может быть null.
	 * 
	 * @param topLeft
	 * @param bottomRight
	 * @param lines
	 * @param selected
	 */
	public ComboBox(Point topLeft, Point bottomRight, String[] lines, Integer selected) {
		this(topLeft, bottomRight, true, lines, selected);
	}

	/**
	 * Создает активный ComboBox по координатам левого верхнего угла, ширине,
	 * высоте, набору строк и номеру выделенной строки. Если выделенной строки нет,
	 * в качестве “selected” передается null. Параметр lines может быть null.
	 * 
	 * @param xLeft
	 * @param yTop
	 * @param width
	 * @param height
	 * @param lines
	 * @param selected
	 */
	public ComboBox(int xLeft, int yTop, int width, int height, String[] lines, Integer selected) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, lines, selected);
	}

	/**
	 * Возвращает номер выбранной строки ComboBox.
	 * 
	 * @return
	 */
	public Integer getSelected() {
		return selected;
	}

	/**
	 * Устанавливает номер выбранной строки ComboBox.
	 * 
	 * @param selected
	 */
	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
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
		ComboBox other = (ComboBox) obj;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		return true;
	}

}
