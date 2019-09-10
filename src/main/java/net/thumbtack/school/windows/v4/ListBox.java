package net.thumbtack.school.windows.v4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import net.thumbtack.school.windows.v4.base.RectWindow;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class ListBox extends RectWindow {

    private String[] lines;

    /**
     * Создает ListBox по координатам углов - левого верхнего и правого нижнего,
     * флагу активности и набору строк. Обращаем внимание на то, что обе точки
     * входят в ListBox, так что если создать ListBox с topLeft.equals(bottomRight),
     * то будет создан ListBox ширины и высоты 1. Параметр lines может быть null.
     *
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param lines
     */
    public ListBox(Point topLeft, Point bottomRight, WindowState active, String[] lines) throws WindowException {
        if ((active == WindowState.DESTROYED) || (active == null)) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        setTopLeft(new Point(topLeft));
        setState(active);
        setBottomRight(new Point(bottomRight));
        setLines(lines);
    }

    /**
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param lines
     * @throws WindowException
     */
    public ListBox(Point topLeft, Point bottomRight, String active, String[] lines) throws WindowException {
        this(topLeft, bottomRight, WindowState.fromString(active), lines);
    }

    /**
     * Создает ListBox по координатам левого верхнего угла, ширине, высоте, флагу
     * активности и набору строк. Параметр lines может быть null.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param lines
     */
    public ListBox(int xLeft, int yTop, int width, int height, WindowState active, String[] lines)
            throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines);
    }

    /**
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param lines
     * @throws WindowException
     */
    public ListBox(int xLeft, int yTop, int width, int height, String active, String[] lines) throws WindowException {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), WindowState.fromString(active),
                lines);
    }

    /**
     * Создает активный ListBox по координатам углов - левого верхнего и правого
     * нижнего и набору строк. Параметр lines может быть null.
     *
     * @param topLeft
     * @param bottomRight
     * @param lines
     */
    public ListBox(Point topLeft, Point bottomRight, String[] lines) throws WindowException {
        this(topLeft, bottomRight, WindowState.ACTIVE, lines);
    }

    /**
     * Создает активный ListBox по координатам левого верхнего угла, ширине и высоте
     * и набору строк. Параметр lines может быть null.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param lines
     */
    public ListBox(int xLeft, int yTop, int width, int height, String[] lines) throws WindowException {
        this(xLeft, yTop, width, height, WindowState.ACTIVE, lines);
    }

    /**
     * Возвращает копию набора строк ListBox.
     *
     * @return
     */
    public String[] getLines() {
        return (lines != null) ? lines.clone() : null;
    }

    /**
     * Устанавливает набор строк ListBox.
     *
     * @param lines
     * @throws WindowException
     */
    public void setLines(String[] lines) {
        this.lines = lines != null ? lines.clone() : null;
    }

    /**
     * Возвращает набор строк ListBox, начиная со строки “from” и до строки (“to”-
     * 1) включительно. Если массив строк равен null, выбрасывается исключение
     * WindowException с кодом ошибки EMPTY_ARRAY. Если “from” < 0 или в ListBox
     * строк меньше, чем “to” , или “from” > (“to” - 1), выбрасывается исключение
     * WindowException с кодом ошибки WRONG_INDEX.
     *
     * @param from
     * @param to
     * @return
     */
    public String[] getLinesSlice(int from, int to) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if ((from < 0) || (lines.length < to) || (from >= to)) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        ArrayList<String> s = new ArrayList<String>();
        for (int i = from; i < to; i++) {
            s.add(lines[i]);
        }
        return s.toArray(new String[s.size()]);
    }

    /**
     * Возвращает строку с номером index. Если массив строк равен null,
     * выбрасывается исключение WindowException с кодом ошибки EMPTY_ARRAY. Если
     * строки с таким номером нет, выбрасывается исключение WindowException с кодом
     * ошибки WRONG_INDEX
     *
     * @param index
     * @return
     */
    public String getLine(int index) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if (lines.length <= index) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        return lines[index];
    }

    /**
     * Заменяет строку с номером index. Если массив строк равен null, выбрасывается
     * исключение WindowException с кодом ошибки EMPTY_ARRAY. Если строки с таким
     * номером нет, выбрасывается исключение WindowException с кодом ошибки
     * WRONG_INDEX.
     *
     * @param index
     * @param line
     */
    public void setLine(int index, String line) throws WindowException {
        if (lines == null) {
            throw new WindowException(WindowErrorCode.EMPTY_ARRAY);
        }
        if ((lines.length <= index) || (index < 0)) {
            throw new WindowException(WindowErrorCode.WRONG_INDEX);
        }
        lines[index] = line;
    }

    /**
     * Ищет первую совпадающую с line строку в массиве строк ListBox. Если строка
     * найдена, возвращает ее индекс, в противном случае возвращает null.
     *
     * @param line
     * @return
     */
    public Integer findLine(String line) {
        if ((line == null) || (this.lines == null)) {
            return null;
        }
        for (int i = 0; i < this.lines.length; i++) {
            if (line.equals(this.lines[i])) {
                return i;
            }
        }
        return null;
    }

    /**
     * Переворачивает массив строк ListBox., то есть делает 0-ю строку - последней,
     * первую - предпоследней и т.д. Если массив строк равен null, не делает ничего.
     */
    public void reverseLineOrder() {
        if (lines != null) {
            ArrayList<String> aList = new ArrayList<String>(Arrays.asList(lines));
            Collections.reverse(aList);
            lines = aList.stream().toArray(String[]::new);
        }
    }

    /**
     * Переворачивает каждую строку в массиве строк ListBox.Если массив строк равен
     * null, не делает ничего.
     */
    public void reverseLines() {
        if (lines != null) {
            for (int i = 0; i < lines.length; i++) {
                lines[i] = new StringBuilder(lines[i]).reverse().toString();
            }
        }
    }

    /**
     * Заменяет массив строк на новый массив, вместо каждой строки вставлены две
     * копии ее. Если массив строк равен null, не делает ничего.
     */
    public void duplicateLines() {
        if (lines != null) {
            String[] str = new String[lines.length * 2];
            for (int i = 0; i < lines.length; i++) {
                str[i * 2] = str[i * 2 + 1] = lines[i];
            }
            lines = str;
        }
    }

    /**
     * Заменяет массив строк на новый массив, в котором каждая нечетная исходная
     * строка удалена. Если массив строк равен null или содержит только одну строку,
     * не делает ничего.
     */
    public void removeOddLines() {
        if (lines != null) {
            String[] str = new String[(lines.length + 1) / 2];
            for (int i = 0; i < str.length; i++) {
                str[i] = lines[i * 2];
            }
            lines = str;
        }
    }

    /**
     * Возвращает true, если массив строк строго упорядочен по убыванию, иначе
     * false. Если массив строк равен null, возвращает true.
     *
     * @return
     */
    public boolean isSortedDescendant() {
        if (lines != null) {
            for (int i = 1; i < lines.length; i++) {
                if (lines[i - 1].compareTo(lines[i]) <= 0)
                    return false;
            }
        }
        return true;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ListBox listBox = (ListBox) o;
		return Arrays.equals(lines, listBox.lines);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
		result = 31 * result + Arrays.hashCode(lines);
        return result;
    }
}
