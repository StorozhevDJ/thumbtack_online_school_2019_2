package net.thumbtack.school.windows.v3;

public class RadioButton extends RoundButton {

	private boolean checked;

	/**
	 * Создает RadioButton по координатам центра, значению радиуса, флагу
	 * активности, тексту и состоянию.
	 * 
	 * @return
	 */
	public RadioButton(Point center, int radius, boolean active, String text, boolean checked) {
		super(center, radius, active, text);
		this.checked = checked;
	}

	/**
	 * Создает RadioButton по координатам центра, значению радиуса, флагу
	 * активности, тексту и состоянию.
	 * 
	 * @param xCenter
	 * @param yCenter
	 * @param radius
	 * @param active
	 * @param text
	 * @param checked
	 */
	public RadioButton(int xCenter, int yCenter, int radius, boolean active, String text, boolean checked) {
		this(new Point(xCenter, yCenter), radius, active, text, checked);
	}

	/**
	 * Создает активную RadioButton по координатам центра, значению радиуса, тексту
	 * и состоянию.
	 * 
	 * @param center
	 * @param radius
	 * @param text
	 * @param checked
	 */
	public RadioButton(Point center, int radius, String text, boolean checked) {
		this(center, radius, true, text, checked);
	}

	/**
	 * Создает активную RadioButton по координатам центра, значению радиуса, тексту
	 * и состоянию.
	 * 
	 * @param xCenter
	 * @param yCenter
	 * @param radius
	 * @param text
	 * @param checked
	 */
	public RadioButton(int xCenter, int yCenter, int radius, String text, boolean checked) {
		this(new Point(xCenter, yCenter), radius, true, text, checked);
	}

	/**
	 * Возвращает true, если точка внутри кнопки установлена, иначе false.
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Устанавливает или убирает точку внутри RadioButton.
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (checked ? 1231 : 1237);
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
		RadioButton other = (RadioButton) obj;
		if (checked != other.checked)
			return false;
		return true;
	}



}
