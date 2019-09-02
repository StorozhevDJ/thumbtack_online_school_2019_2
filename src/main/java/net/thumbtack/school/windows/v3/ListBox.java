package net.thumbtack.school.windows.v3;

import net.thumbtack.school.windows.v3.base.RectWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
	public ListBox(Point topLeft, Point bottomRight, boolean active, String[] lines) {
		setTopLeft(new Point(topLeft));
		setBottomRight(new Point(bottomRight));
		setActive(active);
		setLines(lines);
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
	public ListBox(int xLeft, int yTop, int width, int height, boolean active, String[] lines) {
		this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, lines);
	}

	/**
	 * Создает активный ListBox по координатам углов - левого верхнего и правого
	 * нижнего и набору строк. Параметр lines может быть null.
	 *
	 * @param topLeft
	 * @param bottomRight
	 * @param lines
	 */
	public ListBox(Point topLeft, Point bottomRight, String[] lines) {
		this(topLeft, bottomRight, true, lines);
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
	public ListBox(int xLeft, int yTop, int width, int height, String[] lines) {
		this(xLeft, yTop, width, height, true, lines);
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
	 */
	public void setLines(String[] lines) {
		this.lines = (lines != null) ? lines.clone() : null;
	}

	/**
	 * Возвращает набор строк ListBox, начиная со строки “from” и до строки (“to”-1)
	 * включительно . Если в ListBox строк меньше, чем “to”, возвращает строки от
	 * “from” и до конца. Гарантируется, что “from” < “to”. Если массив строк равен
	 * null, возвращает null.
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public String[] getLinesSlice(int from, int to) {
		if (lines == null)
			return null;
		ArrayList<String> s = new ArrayList<String>();
		for (int i = from; (i < lines.length) && (i < to); i++) {
			s.add(lines[i]);
		}
		return s.toArray(new String[s.size()]);
	}

	/**
	 * Возвращает строку с номером index. Если строки с таким номером нет или массив
	 * строк равен null, возвращает null.
	 *
	 * @param index
	 * @return
	 */
	public String getLine(int index) {
		return (lines.length > index) ? lines[index] : null;
	}

	/**
	 * Заменяет строку с номером index. Если строки с таким номером нет или массив
	 * строк равен null, ничего не делает.
	 *
	 * @param index
	 * @param line
	 */
	public void setLine(int index, String line) {
		if ((lines != null) && (lines.length > index)) {
			lines[index] = line;
		}
	}

	/**
	 * Ищет первую совпадающую с line строку в массиве строк ListBox. Если строка
	 * найдена, возвращает ее индекс, в противном случае возвращает null.
	 *
	 * @param line
	 * @return
	 */
	public Integer findLine(String line) {
		if ((line == null) || (this.lines == null))
			return null;
		for (int i = 0; i < this.lines.length; i++) {
			if (line.equals(this.lines[i]))
				return i;
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

	/**
	 * Определяет, пересекается ли ListBox с другим ListBox. Считается, что
	 * ListBox’ы пересекаются, если у них есть хоть одна общая точка.
	 *
	 * @param listBox
	 * @return
	 */
	public boolean isIntersects(ListBox listBox) {
		if (isInside(listBox.getBottomRight()) || isInside(listBox.getTopLeft()))
			return true;
		return listBox.isInside(getTopLeft()) || listBox.isInside(getBottomRight());
	}

	/**
	 * Определяет, лежит ли ListBox целиком внутри текущего ListBox.
	 *
	 * @param listBox
	 * @return
	 */
	public boolean isInside(ListBox listBox) {
		return isInside(listBox.getTopLeft()) && isInside(listBox.getBottomRight());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(lines);
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
		ListBox other = (ListBox) obj;
		if (!Arrays.equals(lines, other.lines))
			return false;
		return true;
	}

}
