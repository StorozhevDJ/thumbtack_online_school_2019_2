package net.thumbtack.school.windows.v2;

public class RectButton {

    private Point topLeft, bottomRight;
    private boolean active;
    String text;

    /**
     * Создает RectButton по координатам углов - левого верхнего и правого нижнего,
     * и флагу активности. Обращаем внимание на то, что обе точки входят в кнопку,
     * так что если создать кнопку с topLeft.equals(bottomRight), то будет создана
     * кнопка ширины и высоты 1.
     *
     * @param topLeft
     * @param bottomRight
     * @param active
     */
    public RectButton(Point topLeft, Point bottomRight, boolean active, String text) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.active = active;
        this.text = text;
    }

    /**
     * Создает RectButton по координатам левого верхнего угла, ширине, высоте и
     * флагу активности.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     */
    public RectButton(int xLeft, int yTop, int width, int height, boolean active, String text) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text);
    }

    /**
     * Создает активную RectButton по координатам углов - левого верхнего и правого
     * нижнего.
     *
     * @param topLeft
     * @param bottomRight
     */
    public RectButton(Point topLeft, Point bottomRight, String text) {
        this(topLeft, bottomRight, true, text);
    }

    /**
     * Создает активную RectButton по координатам левого верхнего угла, ширине и
     * высоте.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     */
    public RectButton(int xLeft, int yTop, int width, int height, String text) {
        this(xLeft, yTop, width, height, true, text);
    }

    /**
     * Возвращает левую верхнюю точку RectButton.
     *
     * @return
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Возвращает правую нижнюю точку RectButton.
     *
     * @return
     */
    public Point getBottomRight() {
        return bottomRight;
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
     * Устанавливает левую верхнюю точку RectButton.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Устанавливает правую нижнюю точку RectButton.
     *
     * @param bottomRight
     */
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    /**
     * Устанавливает состояние активности RectButton.
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает ширину RectButton.
     *
     * @return
     */
    public int getWidth() {
        return bottomRight.getX() - topLeft.getX() + 1;
    }

    /**
     * Возвращает высоту RectButton.
     *
     * @return
     */
    public int getHeight() {
        return bottomRight.getY() - topLeft.getY() + 1;
    }

    /**
     * Передвигает RectButton так, чтобы левый верхний угол его оказался в точке (x,
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
     * Передвигает RectButton так, чтобы левый верхний угол его оказался в точке
     * point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        moveTo(point.getX(), point.getY());
    }

    /**
     * Передвигает RectButton на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        topLeft.moveRel(dx, dy);
        bottomRight.moveRel(dx, dy);
    }

    /**
     * Изменяет ширину и длину RectButton в ratio раз при сохранении координат левой
     * верхней точки. Дробная часть вычисленной длины или ширины отбрасывается. Если
     * при таком изменении длина или ширина какой-то из сторон окажется меньше 1, то
     * она принимается равной 1.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        int newWigth = (int) (getWidth() * ratio);
        int newHeight = (int) (getHeight() * ratio);
        bottomRight.moveTo((newWigth >= 1) ? newWigth + topLeft.getX() - 1 : topLeft.getX(), (newHeight >= 1) ? newHeight + topLeft.getY() - 1 : topLeft.getY());
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри RectButton. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isInside(int x, int y) {
        if ((x < topLeft.getX()) || (x > bottomRight.getX())) return false;
        return (y >= topLeft.getY()) && (y <= bottomRight.getY());
    }

    /**
     * Определяет, лежит ли точка point внутри RectButton. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

    /**
     * Определяет, пересекается ли RectButton с другим RectButton. Считается, что
     * кнопки пересекаются, если у них есть хоть одна общая точка.
     *
     * @param rectButton
     * @return
     */
    public boolean isIntersects(RectButton rectButton) {
        if (isInside(rectButton.getBottomRight()) || isInside(rectButton.getTopLeft())) return true;
        return rectButton.isInside(this.topLeft) || rectButton.isInside(this.bottomRight);
    }

    /**
     * Определяет, лежит ли RectButton целиком внутри текущего RectButton.
     *
     * @param rectButton
     * @return
     */
    public boolean isInside(RectButton rectButton) {
        return isInside(rectButton.getTopLeft()) && isInside(rectButton.getBottomRight());
    }

    /**
     * Определяет, верно ли, что вся RectButton находится в пределах Desktop.
     *
     * @param desktop
     * @return
     */
    public boolean isFullyVisibleOnDesktop(Desktop desktop) {
        if ((topLeft.getX() < 0) || (topLeft.getY() < 0)) return false;
        return (bottomRight.getX() <= desktop.getWidth()) && (bottomRight.getY() <= desktop.getHeight());
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

        RectButton that = (RectButton) o;

        if (active != that.active) return false;
        if (topLeft != null ? !topLeft.equals(that.topLeft) : that.topLeft != null) return false;
        return bottomRight != null ? bottomRight.equals(that.bottomRight) : that.bottomRight == null;
    }

    @Override
    public int hashCode() {
        int result = topLeft != null ? topLeft.hashCode() : 0;
        result = 31 * result + (bottomRight != null ? bottomRight.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
