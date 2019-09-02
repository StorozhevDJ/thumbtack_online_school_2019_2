package net.thumbtack.school.windows.v3;

public class Point {
	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
	}

	public Point(Point point) {
		this(point.getX(), point.getY());
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
		if ((x >= desktop.getWidth()) || (x < 0))
			return false;
		return (y < desktop.getHeight()) && (y >= 0);
	}

	public boolean isNotVisibleOnDesktop(Desktop desktop) {
		return !isVisibleOnDesktop(desktop);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
