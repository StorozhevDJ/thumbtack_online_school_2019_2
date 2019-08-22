package net.thumbtack.school.windows.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListBox {

    private Point topLeft, bottomRight;
    private boolean active;
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
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.active = active;
        this.lines = (lines != null) ? lines.clone() : null;
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
     * Возвращает левую верхнюю точку ListBox.
     *
     * @return
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Возвращает правую нижнюю точку ListBox.
     *
     * @return
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Возвращает true, если ListBox активен, иначе false
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Устанавливает левую верхнюю точку ListBox.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Устанавливает правую нижнюю точку ListBox.
     *
     * @param bottomRight
     */
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    /**
     * Устанавливает состояние активности ListBox.
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает ширину ListBox.
     *
     * @return
     */
    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    /**
     * Возвращает высоту ListBox.
     *
     * @return
     */
    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
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
        this.lines = lines;
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
     * Передвигает ListBox так, чтобы левый верхний угол его оказался в точке (x,
     * y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        bottomRight.moveRel(x - topLeft.getX(), y - topLeft.getY());
        topLeft.moveTo(x, y);
    }

    /**
     * Передвигает ListBox так, чтобы левый верхний угол его оказался в точке point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        bottomRight.moveRel(point.getX() - topLeft.getX(), point.getY() - topLeft.getY());
        topLeft = point;
    }

    /**
     * Передвигает ListBox на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        topLeft.moveRel(dx, dy);
        bottomRight.moveRel(dx, dy);
    }

    /**
     * Изменяет ширину и длину ListBox в ratio раз при сохранении координат левой
     * верхней точки. Дробная часть вычисленной длины или ширины отбрасывается. Если
     * при таком изменении длина или ширина какой-то из сторон окажется меньше 1, то
     * она принимается равной 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        int newWigth = (int) (getWidth() * ratio);
        int newHeight = (int) (getHeight() * ratio);
        bottomRight.moveTo((newWigth >= 1) ? newWigth + topLeft.getX() - 1 : topLeft.getX(),
                (newHeight >= 1) ? newHeight + topLeft.getY() - 1 : topLeft.getY());
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри ListBox. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {
        if ((x < topLeft.getX()) || (x > bottomRight.getX()))
            return false;
        return (y >= topLeft.getY()) && (y <= bottomRight.getY());
    }

    /**
     * Определяет, лежит ли точка point внутри ListBox. Если точка лежит на стороне,
     * считается, что она лежит внутри.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
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
        return listBox.isInside(this.topLeft) || listBox.isInside(this.bottomRight);
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

    /**
     * Определяет, верно ли, что весь ListBox находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        if ((topLeft.getX() < 0) || (topLeft.getY() < 0))
            return false;
        return (bottomRight.getX() <= desktop.getWidth()) && (bottomRight.getY() <= desktop.getHeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListBox listBox = (ListBox) o;

        if (active != listBox.active) return false;
        if (topLeft != null ? !topLeft.equals(listBox.topLeft) : listBox.topLeft != null) return false;
        if (bottomRight != null ? !bottomRight.equals(listBox.bottomRight) : listBox.bottomRight != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(lines, listBox.lines);
    }

    @Override
    public int hashCode() {
        int result = topLeft != null ? topLeft.hashCode() : 0;
        result = 31 * result + (bottomRight != null ? bottomRight.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + Arrays.hashCode(lines);
        return result;
    }
}
