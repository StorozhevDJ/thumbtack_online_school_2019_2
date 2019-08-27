package net.thumbtack.school.windows.v2;

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
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param lines
     * @param selected
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
     * Возвращает левую верхнюю точку ComboBox.
     *
     * @return
     */
    public Point getTopLeft() {
        return super.getTopLeft();
    }

    /**
     * Возвращает правую нижнюю точку ComboBox.
     *
     * @return
     */
    public Point getBottomRight() {
        return super.getBottomRight();
    }

    /**
     * Возвращает true, если ComboBox активен, иначе false.
     *
     * @return
     */
    public boolean isActive() {
        return super.isActive();
    }

    /**
     * Устанавливает левую верхнюю точку ComboBox.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        super.setTopLeft(topLeft);
    }

    /**
     * Устанавливает правую нижнюю точку ComboBox.
     *
     * @param bottomRight
     */
    public void setBottomRight(Point bottomRight) {
        super.setBottomRight(bottomRight);
    }

    /**
     * Устанавливает состояние активности ComboBox.
     *
     * @param active
     */
    public void setActive(boolean active) {
        super.setActive(active);
    }

    /**
     * Возвращает ширину ComboBox.
     *
     * @return
     */
    public int getWidth() {
        return super.getWidth();
    }

    /**
     * Возвращает высоту ComboBox.
     *
     * @return
     */
    public int getHeight() {
        return super.getHeight();
    }

    /**
     * Возвращает копию набора строк ComboBox.
     *
     * @return
     */
    public String[] getLines() {
        return super.getLines();
    }

    /**
     * Устанавливает набор строк ComboBox.
     *
     * @param lines
     */
    public void setLines(String[] lines) {
        super.setLines(lines);
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

    /**
     * Возвращает набор строк ComboBox, начиная со строки “from” и до строки
     * (“to”-1) включительно. Если в ComboBox строк меньше, чем “to”, возвращает
     * строки от “from” и до конца. Гарантируется, что “from” < “to”. Если массив
     * строк равен null, возвращает null.
     *
     * @param from
     * @param to
     * @return
     */
    public String[] getLinesSlice(int from, int to) {
        return super.getLinesSlice(from, to);
    }

    /**
     * Возвращает строку с номером index. Если строки с таким номером нет или массив
     * строк равен null, возвращает null.
     *
     * @param index
     * @return
     */
    public String getLine(int index) {
        return super.getLine(index);
    }

    /**
     * Заменяет строку с номером index. Если строки с таким номером нет или массив
     * строк равен null,, ничего не делает.
     *
     * @param index
     * @param line
     */
    public void setLine(int index, String line) {
        super.setLine(index, line);
    }

    /**
     * Ищет первую совпадающую с line строку в массиве строк ComboBox. Если строка
     * найдена, возвращает ее индекс, в противном случае возвращает null.
     *
     * @param line
     * @return
     */
    public Integer findLine(String line) {
        return super.findLine(line);
    }

    /**
     * Переворачивает массив строк ComboBox., то есть делает 0-ю строку - последней,
     * первую - предпоследней и т.д. Если массив строк равен null, не делает ничего.
     */
    public void reverseLineOrder() {
        super.reverseLineOrder();
    }

    /**
     * Переворачивает каждую строку в массиве строк ComboBox. Если массив строк
     * равен null, не делает ничего.
     */
    public void reverseLines() {
        super.reverseLines();
    }

    /**
     * Заменяет массив строк на новый массив, вместо каждой строки вставлены две
     * копии ее. Если массив строк равен null, не делает ничего.
     */
    public void duplicateLines() {
        super.duplicateLines();
    }

    /**
     * Заменяет массив строк на новый массив, в котором каждая нечетная исходная
     * строка удалена. Если массив строк равен null или содержит только одну строку,
     * не делает ничего.
     */
    public void removeOddLines() {
        super.removeOddLines();
    }

    /**
     * Возвращает true, если массив строк строго упорядочен по убыванию, иначе
     * false. Если массив строк равен null, возвращает true.
     *
     * @return
     */
    public boolean isSortedDescendant() {
        return super.isSortedDescendant();
    }

    /**
     * Передвигает ComboBox так, чтобы левый верхний угол его оказался в точке (x,
     * y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        super.moveTo(x, y);
    }

    /**
     * Передвигает ComboBox так, чтобы левый верхний угол его оказался в точке
     * point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        super.moveTo(point);
    }

    /**
     * Передвигает ComboBox на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }

    /**
     * Изменяет ширину и длину ComboBox в ratio раз при сохранении координат левой
     * верхней точки. Дробная часть вычисленной длины или ширины отбрасывается. Если
     * при таком изменении длина или ширина какой-то из сторон окажется меньше 1, то
     * она принимается равной 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        super.resize(ratio);
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри ComboBox. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {
        return super.isInside(x, y);
    }

    /**
     * Определяет, лежит ли точка point внутри ComboBox. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return super.isInside(point);
    }

    /**
     * Определяет, пересекается ли ComboBox с другим ComboBox. Считается, что
     * ComboBox’ы пересекаются, если у них есть хоть одна общая точка.
     *
     * @param comboBox
     * @return
     */
    public boolean isIntersects(ComboBox comboBox) {
        return super.isIntersects(comboBox);
    }

    /**
     * Определяет, лежит ли ComboBox целиком внутри текущего ComboBox.
     *
     * @param comboBox
     * @return
     */
    public boolean isInside(ComboBox comboBox) {
        return super.isInside(comboBox);
    }

    /**
     * Определяет, верно ли, что весь ComboBox находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        return super.isFullyVisibleOnDesktop(desktop);
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
            return other.selected == null;
        } else return selected.equals(other.selected);
    }

    // методы equals и hashCode.

}
