package net.thumbtack.school.windows.v2;

public class RectButton3D extends RectButton {

    private int zHeight;

    /**
     * Создает RectButton3D по координатам углов - левого верхнего и правого
     * нижнего, высоте zHeight, строке на кнопке text и флагу активности. Обращаем
     * внимание на то, что обе точки входят в кнопку, так что если создать кнопку с
     * topLeft.equals(bottomRight), то будет создана кнопка ширины и высоты 1.
     *
     * @param topLeft
     * @param bottomRight
     * @param active
     * @param text
     * @param zHeight
     */
    public RectButton3D(Point topLeft, Point bottomRight, boolean active, String text, int zHeight) {
        super(topLeft, bottomRight, active, text);
        setZHeight(zHeight);
    }

    /**
     * Создает RectButton3D по координатам левого верхнего угла, ширине, высоте,
     * строке на кнопке text, высоте zHeight и флагу активности.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param active
     * @param text
     * @param zHeight
     */
    public RectButton3D(int xLeft, int yTop, int width, int height, boolean active, String text, int zHeight) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), active, text, zHeight);
    }

    /**
     * Создает активную RectButton3D по координатам углов - левого верхнего и
     * правого нижнего, строке на кнопке text и высоте zHeight.
     *
     * @param topLeft
     * @param bottomRight
     * @param text
     * @param zHeight
     */
    public RectButton3D(Point topLeft, Point bottomRight, String text, int zHeight) {
        this(topLeft, bottomRight, true, text, zHeight);
    }

    /**
     * Создает активную RectButton3D по координатам левого верхнего угла, ширине,
     * строке на кнопке text и высоте zHeight.
     *
     * @param xLeft
     * @param yTop
     * @param width
     * @param height
     * @param text
     * @param zHeight
     */
    public RectButton3D(int xLeft, int yTop, int width, int height, String text, int zHeight) {
        this(new Point(xLeft, yTop), new Point(xLeft + width - 1, yTop + height - 1), true, text, zHeight);
    }

    /**
     * Возвращает левую верхнюю точку RectButton3D.
     *
     * @return
     */
    public Point getTopLeft() {
        return super.getTopLeft();
    }

    /**
     * Возвращает правую нижнюю точку RectButton3D.
     *
     * @return
     */
    public Point getBottomRight() {
        return super.getBottomRight();
    }

    /**
     * Возвращает true, если кнопка активна, иначе false
     *
     * @return
     */
    public boolean isActive() {
        return super.isActive();
    }

    /**
     * Устанавливает левую верхнюю точку RectButton3D.
     *
     * @param topLeft
     */
    public void setTopLeft(Point topLeft) {
        super.setTopLeft(topLeft);
    }

    /**
     * Устанавливает правую нижнюю точку RectButton3D.
     *
     * @param bottomRight
     */
    public void setBottomRight(Point bottomRight) {
        super.setBottomRight(bottomRight);
    }

    /**
     * Устанавливает состояние активности RectButton3D.
     *
     * @param active
     */
    public void setActive(boolean active) {
        super.setActive(active);
    }

    /**
     * Возвращает ширину RectButton3D.
     *
     * @return
     */
    public int getWidth() {
        return super.getWidth();
    }

    /**
     * Возвращает высоту RectButton3D.
     *
     * @return
     */
    public int getHeight() {
        return super.getHeight();
    }

    /**
     * Возвращает высоту RectButton3D по оси Z.
     *
     * @return
     */
    public int getZHeight() {
        return zHeight;
    }

    /**
     * Устанавливает высоту RectButton3D по оси Z.
     *
     * @param zHeight
     */
    public void setZHeight(int zHeight) {
        this.zHeight = zHeight;
    }

    /**
     * Возвращает текст окна
     *
     * @return
     */
    public String getText() {
        return super.getText();
    }

    /**
     * Устанавливает текст окна
     *
     * @param text
     */
    public void setText(String text) {
        super.setText(text);
    }

    /**
     * Передвигает RectButton3D так, чтобы левый верхний угол его оказался в точке
     * (x, y).
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        super.moveTo(x, y);
    }

    /**
     * Передвигает RectButton3D так, чтобы левый верхний угол его оказался в точке
     * point.
     *
     * @param point
     */
    public void moveTo(Point point) {
        super.moveTo(point);
    }

    /**
     * Передвигает RectButton3D на (dx, dy).
     *
     * @param dx
     * @param dy
     */
    public void moveRel(int dx, int dy) {
        super.moveRel(dx, dy);
    }

    /**
     * Изменяет ширину и длину RectButton3D в ratio раз при сохранении координат
     * левой верхней точки. Дробная часть вычисленной длины или ширины
     * отбрасывается. Е сли при таком изменении длина или ширина какой-то из сторон
     * окажется меньше 1, то она принимается равной 1. Высота не изменяется.
     *
     * @param ratio
     */
    public void resize(double ratio) {
        super.resize(ratio);
    }

    /**
     * Определяет, лежит ли точка (x, y) внутри RectButton3D. Если точка лежит на
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
     * Определяет, лежит ли точка point внутри RectButton3D. Если точка лежит на
     * стороне, считается, что она лежит внутри.
     *
     * @param point
     * @return
     */
    public boolean isInside(Point point) {
        return super.isInside(point);
    }

    /**
     * Определяет, пересекается ли RectButton3D с другим RectButton или
     * RectButton3D. Считается, что кнопки пересекаются, если у них есть хоть одна
     * общая точка.
     *
     * @param rectButton
     * @return
     */
    public boolean isIntersects(RectButton rectButton) {
        return super.isIntersects(rectButton);
    }

    /**
     * Определяет, лежит ли RectButton целиком внутри текущего RectButton3D.
     * Поскольку RectButton не имеет z-высоты, она при сравнении не учитывается.
     *
     * @param rectButton
     * @return
     */
    public boolean isInside(RectButton rectButton) {
        return super.isInside(rectButton);
    }

    /**
     * Определяет, лежит ли RectButton3D целиком внутри текущего RectButton3D.
     * Высота учитывается.
     *
     * @param rectButton3D
     * @return
     */
    public boolean isInside(RectButton3D rectButton3D) {
        return (super.isInside(rectButton3D) && (zHeight >= rectButton3D.getZHeight()));
    }

    /**
     * Определяет, верно ли, что вся RectButton3D находится в пределах Desktop.
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
        result = prime * result + zHeight;
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
        RectButton3D other = (RectButton3D) obj;
        return zHeight == other.zHeight;
    }

    /**
     * методы equals и hashCode.
     */

}
