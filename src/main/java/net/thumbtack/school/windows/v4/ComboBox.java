package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class ComboBox extends ListBox {

    private Integer selected;

    /**
     * Если параметр lines равен null, параметр “selected” может быть только null,
     * иначе выбрасывается исключение WindowException с кодом ошибки EMPTY_ARRAY
     * Если делается попытка установить “selected”, больший чем (число строк - 1),
     * выбрасывается исключение WindowException с кодом ошибки WRONG_INDEX,
     *
     * @return
     */
    public ComboBox(Point topLeft, Point bottomRight, WindowState active, String[] lines, Integer selected)
            throws WindowException {
        super(topLeft, bottomRight, active, lines);
        if (lines == null) {
            if (selected != null) {
                throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
            }
        } else if ((selected != null) && ((lines.length <= selected) || (selected < 0))) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        this.selected = selected;
    }

    public ComboBox(Point topLeft, Point bottomRight, String active, String[] lines, Integer selected)
            throws WindowException {
        this(topLeft, bottomRight, WindowState.fromString(active), lines, selected);
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
    public ComboBox(int xLeft, int yTop, int width, int height, WindowState active, String[] lines, Integer selected)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines, selected);
    }

    public ComboBox(int xLeft, int yTop, int width, int height, String active, String[] lines, Integer selected)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.fromString(active),
                lines, selected);
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
    public ComboBox(Point topLeft, Point bottomRight, String[] lines, Integer selected) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, lines, selected);
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
    public ComboBox(int xLeft, int yTop, int width, int height, String[] lines, Integer selected)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.ACTIVE, lines,
                selected);
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
     * В результате выполнения этого метода номер выбранной строки всегда
     * устанавливается в null.
     */
    @Override
    public void setLines(String[] lines) {
        selected = null;
        super.setLines(lines);
    }

    /**
     * Устанавливает номер выбранной строки ComboBox. Выбрасывается исключение
     * WindowException с кодом ошибки EMPTY_ARRAY, если текущее значение lines равно
     * null, а значение параметра selected не равен null. Выбрасывается исключение
     * WindowException с кодом ошибки WRONG_INDEX, если делается попытка установить
     * номер, больший чем (число строк - 1).
     *
     * @param selected
     * @throws WindowException
     */
    public void setSelected(Integer selected) throws WindowException {
        if ((getLines().length == 0) && (selected != null)) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if ((selected != null) && ((selected < 0) || (getLines().length <= selected))) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
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
