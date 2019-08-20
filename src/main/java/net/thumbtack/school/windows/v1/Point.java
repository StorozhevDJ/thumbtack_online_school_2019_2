package net.thumbtack.school.windows.v1;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public Point(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveRel(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public boolean isVisibleOnDesktop(Desktop desktop) {
        if ((x >= desktop.getWidth()) || (x < 0)) return false;
        return (y < desktop.getHeight()) && (y >= 0);
    }

    public boolean isNotVisibleOnDesktop(Desktop desktop) {
        return !isVisibleOnDesktop(desktop);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
