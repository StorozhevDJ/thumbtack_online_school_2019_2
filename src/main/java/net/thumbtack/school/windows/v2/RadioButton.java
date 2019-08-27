package net.thumbtack.school.windows.v2;

public class RadioButton extends RoundButton {

    private boolean checked;

    /**
     * Создает RadioButton по координатам центра, значению радиуса, флагу
     * активности, тексту и состоянию.
     *
     * @param center
     * @param radius
     * @param active
     * @param text
     * @param checked
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
     * Возвращает центр RadioButton.
     *
     * @return
     */
    public Point getCenter() {
        return super.getCenter();
    }

    /**
     * Возвращает радиус RadioButton.
     *
     * @return
     */
    public int getRadius() {
        return super.getRadius();
    }

    /**
     * Возвращает true, если кнопка активна, иначе false.
     *
     * @return
     */
    public boolean isActive() {
        return super.isActive();
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
     * Передвигает RadioButton так, чтобы центр его оказался в точке (x, y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        super.moveTo(x, y);
    }

    /**
     * Передвигает RadioButton так, чтобы центр его оказался в точке point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        super.moveTo(point);
    }

    /**
     * Передвигает RadioButton на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }

    /**
     * Устанавливает центр RadioButton.
     *
     * @param center
     */
    public void setCenter(Point center) {
        super.setCenter(center.getX(), center.getY());
    }

    /**
     * Устанавливает радиус RadioButton.
     *
     * @param radius
     */
    public void setRadius(int radius) {
        super.setRadius(radius);
    }

    /**
     * Устанавливает состояние активности RadioButton.
     *
     * @param active
     */
    public void setActive(boolean active) {
        super.setActive(active);
    }

    /**
     * Устанавливает или убирает точку внутри RadioButton.
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Изменяет радиус RadioButton в ratio раз, не изменяя центра. Дробная часть
     * вычисленного таким образом радиуса отбрасывается. Если вычисленный таким
     * образом радиус окажется меньше 1, то он принимается равным 1
     *
     * @param ratio
     */
    public void resize(double ratio) {
        super.resize(ratio);
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри RadioButton. Если точка лежит на
     * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
     * пиксельной структурой изображения и рассматриваем RadioButton как
     * математический круг.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {
        return super.isInside(x, y);
    }

    /**
     * Определяет, лежит ли точка point внутри RadioButton. Если точка лежит на
     * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
     * пиксельной структурой изображения и рассматриваем RadioButton как
     * математический круг.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return super.isInside(point);
    }

    /**
     * Определяет, верно ли, что вся RadioButton находится в пределах Desktop.
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
        return checked == other.checked;
    }

    // методы equals и hashCode.

}
