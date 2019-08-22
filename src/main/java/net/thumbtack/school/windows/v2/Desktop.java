package net.thumbtack.school.windows.v2;

public class Desktop {

    private int width, height;

    public Desktop(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Desktop() {
        width = 640;
        height = 480;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return height * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Desktop desktop = (Desktop) o;

        if (width != desktop.width) return false;
        return height == desktop.height;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
