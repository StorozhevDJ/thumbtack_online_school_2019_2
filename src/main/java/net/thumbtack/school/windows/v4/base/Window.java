package net.thumbtack.school.windows.v4.base;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.Point;
import net.thumbtack.school.windows.v4.iface.Movable;
import net.thumbtack.school.windows.v4.iface.Resizable;

import java.util.Objects;

public abstract class Window implements Movable, Resizable {

    private WindowState active;

    /**
     * Возвращает true, если кнопка активна, иначе false.
     *
     * @return
     */
    public WindowState getState() {
        return active;
    }

    /**
     * Устанавливает состояние активности RectButton.
     *
     * @param active
     * @throws WindowException
     */
    public void setState(WindowState active) throws WindowException {
        if ((this.active == WindowState.DESTROYED) && (active != WindowState.DESTROYED) || active == null) {
            throw new WindowException(WindowErrorCode.WRONG_STATE);
        }
        this.active = active;
    }

    public abstract void moveTo(int i, int i1);

	public abstract boolean isFullyVisibleOnDesktop(Desktop desktop);

	public abstract boolean isInside(int x, int y);

	public abstract boolean isInside(Point point);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Window window = (Window) o;
		return active == window.active;
	}

    @Override
    public int hashCode() {
		return Objects.hash(active);
    }
}
