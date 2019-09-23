package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.Desktop;
import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowErrorCode;
import net.thumbtack.school.windows.v4.base.WindowException;

public class PairManager<T extends Window, V extends Window> {

    private T firstWindow;
    private V secondWindow;

    public PairManager(T firstWindow, V secondWindow) throws WindowException {
        super();
        setFirstWindow(firstWindow);
        setSecondWindow(secondWindow);
    }

    public T getFirstWindow() {
        return firstWindow;
    }

    public V getSecondWindow() {
        return secondWindow;
    }

    public void setFirstWindow(T firstWindow) throws WindowException {
        if (firstWindow == null) throw new WindowException(WindowErrorCode.NULL_WINDOW);
        this.firstWindow = firstWindow;
    }

    public void setSecondWindow(V secondWindow) throws WindowException {
        if (secondWindow == null) throw new WindowException(WindowErrorCode.NULL_WINDOW);
        this.secondWindow = secondWindow;
    }

    /**
     * Метод allWindowsFullyVisibleOnDesktop, проверяющий, верно ли, что одновременно находятся на Desktop все окна данного PairManager и еще одного PairManager.
     *
     * @param pairManager
     * @param desktop
     * @return
     */
    public boolean allWindowsFullyVisibleOnDesktop(PairManager<? extends Window, ? extends Window> pairManager, Desktop desktop) {
        return allWindowsFullyVisibleOnDesktop(this, pairManager, desktop);
    }

    /**
     * Статический метод allWindowsFullyVisibleOnDesktop проверяющий, верно ли это для двух разных PairManager.
     *
     * @param pairManager1
     * @param pairManager2
     * @param desktop
     * @return
     */
    public static boolean allWindowsFullyVisibleOnDesktop(PairManager<? extends Window, ? extends Window> pairManager1, PairManager<? extends Window, ? extends Window> pairManager2, Desktop desktop) {
        return pairManager1.getFirstWindow().isFullyVisibleOnDesktop(desktop) &&
                pairManager1.getSecondWindow().isFullyVisibleOnDesktop(desktop) &&
                pairManager2.getFirstWindow().isFullyVisibleOnDesktop(desktop) &&
                pairManager2.getSecondWindow().isFullyVisibleOnDesktop(desktop);
    }
}
