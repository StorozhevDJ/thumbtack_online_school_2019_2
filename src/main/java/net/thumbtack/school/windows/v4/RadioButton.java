package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class RadioButton extends RoundButton {

    private boolean checked;

    /**
     * Создает RadioButton по координатам центра, значению радиуса, флагу
     * активности, тексту и состоянию.
     *
     * @return
     * @throws WindowException
     */
    public RadioButton(Point center, int radius, WindowState active, String text, boolean checked) throws WindowException {
        super(center, radius, active, text);
        this.checked = checked;
    }

    public RadioButton(Point center, int radius, String active, String text, boolean checked) throws WindowException {
        this(center, radius, WindowState.fromString(active), text, checked);
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
     * @throws WindowException
     */
    public RadioButton(int xCenter, int yCenter, int radius, WindowState active, String text, boolean checked) throws WindowException {
        this(new Point(xCenter, yCenter), radius, active, text, checked);
    }

    public RadioButton(int xCenter, int yCenter, int radius, String active, String text, boolean checked) throws WindowException {
        this(new Point(xCenter, yCenter), radius, WindowState.fromString(active), text, checked);
    }

    /**
     * Создает активную RadioButton по координатам центра, значению радиуса, тексту
     * и состоянию.
     *
     * @param center
     * @param radius
     * @param text
     * @param checked
     * @throws WindowException
     */
    public RadioButton(Point center, int radius, String text, boolean checked) throws WindowException {
        this(center, radius, WindowState.ACTIVE, text, checked);
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
     * @throws WindowException
     */
    public RadioButton(int xCenter, int yCenter, int radius, String text, boolean checked) throws WindowException {
        this(new Point(xCenter, yCenter), radius, WindowState.ACTIVE, text, checked);
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
