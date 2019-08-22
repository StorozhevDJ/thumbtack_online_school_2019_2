package net.thumbtack.school.windows.v2;

public class RoundButton {

    private Point center;
    private int radius;
    private boolean active;
    private String text;

    /**
     * Создает RoundButton по координатам центра, значению радиуса и флагу
     * активности.
     *
     * @param center
     * @param radius
     * @param active
     */
    public RoundButton(Point center, int radius, boolean active, String text) {
        this.center = center;
        this.radius = radius;
        this.active = active;
        this.text = text;
    }

    /**
     * Создает RoundButton по координатам центра, значению радиуса и флагу
     * активности.
     *
     * @param xCenter
     * @param yCenter
     * @param radius
     * @param active
     */
    public RoundButton(int xCenter, int yCenter, int radius, boolean active, String text) {
        this.center = new Point(xCenter, yCenter);
        this.radius = radius;
        this.active = active;
        this.text = text;
    }

    /**
     * Создает активную RoundButton по координатам центра и значению радиуса.
     *
     * @param center
     * @param radius
     */
    public RoundButton(Point center, int radius, String text) {
        this(center, radius, true, text);
    }

    /**
     * Создает активную RoundButton по координатам центра и значению радиуса.
     *
     * @param xCenter
     * @param yCenter
     * @param radius
     */
    public RoundButton(int xCenter, int yCenter, int radius, String text) {
        this(new Point(xCenter, yCenter), radius, text);
    }

    /**
     * Возвращает центр RoundButton.
     *
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Возвращает радиус RoundButton.
     *
     * @return
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Возвращает true, если кнопка активна, иначе false.
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Передвигает RoundButton так, чтобы центр его оказался в точке (x, y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        center.moveTo(x, y);
    }

    /**
     * Передвигает RoundButton так, чтобы центр его оказался в точке point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        center.moveTo(point.getX(), point.getY());
    }

    /**
     * Устанавливает центр RoundButton.
     *
     * @param x
     * @param y
     */
    public void setCenter(int x, int y) {
        center.moveTo(x, y);
    }

    /**
     * Устанавливает радиус RoundButton.
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Устанавливает состояние активности RoundButton.
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Передвигает RoundButton на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

    /**
     * Изменяет радиус RoundButton в ratio раз, не изменяя центра. Дробная часть
     * вычисленного таким образом радиуса отбрасывается. Если вычисленный радиус
     * окажется меньше 1, то он принимается равным 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        radius *= ratio;
        if (radius < 1)
            radius = 1;
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри RoundButton. Если точка лежит на
     * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
     * пиксельной структурой изображения и рассматриваем RoundButton как
     * математический круг.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {

        /*
         * x^2+y^2=r^2 (for circle with center in 0) (x-a)^2 + (y-b)^2 = r^2 (for circle
         * with center with x=a, y=b)
         *
         */
        int dx = x - center.getX();
        int dy = y - center.getY();
        return (dx * dx + dy * dy) <= radius * radius;
    }

    /**
     * Определяет, лежит ли точка point внутри RoundButton. Если точка лежит на
     * окружности, считается, что она лежит внутри. В этом методе мы пренебрегаем
     * пиксельной структурой изображения и рассматриваем RoundButton как
     * математический круг.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    /**
     * Определяет, верно ли, что вся RoundButton находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        if (((center.getX() - radius) < 0) || ((center.getX() + radius) >= desktop.getWidth()))
            return false;
        return ((center.getX() - radius) >= 0) && ((center.getX() + radius) < desktop.getWidth());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundButton that = (RoundButton) o;

        if (radius != that.radius) return false;
        if (active != that.active) return false;
        if (center != null ? !center.equals(that.center) : that.center != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = center != null ? center.hashCode() : 0;
        result = 31 * result + radius;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
