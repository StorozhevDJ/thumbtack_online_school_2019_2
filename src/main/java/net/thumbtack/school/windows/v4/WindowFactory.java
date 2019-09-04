package net.thumbtack.school.windows.v4;

import net.thumbtack.school.windows.v4.base.WindowException;
import net.thumbtack.school.windows.v4.base.WindowState;

public class WindowFactory {

    private static int rectButtonCount;
    private static int roundButtonCount;

    /**
     * Создает RectButton по координатам точек и флагу активности.
     *
     * @param leftTop
     * @param rightBottom
     * @param active
     * @return
     * @throws WindowException
     */
    public static RectButton createRectButton(Point leftTop, Point rightBottom, WindowState active, String text) throws WindowException {
        rectButtonCount++;
        return new RectButton(leftTop, rightBottom, active, text);
    }

    /**
     * Создает RoundButton по координатам центра, значению радиуса и флагу
     * активности.
     *
     * @param center
     * @param radius
     * @param active
     * @return
     * @throws WindowException
     */
    public static RoundButton createRoundButton(Point center, int radius, WindowState active, String text) throws WindowException {
        roundButtonCount++;
        return new RoundButton(center, radius, active, text);
    }

    /**
     * Возвращает количество RectButton, созданных с помощью метода
     * createRectButton.
     *
     * @return
     */
    public static int getRectButtonCount() {
        return rectButtonCount;
    }

    /**
     * Возвращает количество RoundButton, созданных с помощью метода
     * createRoundButton.
     *
     * @return
     */
    public static int getRoundButtonCount() {
        return roundButtonCount;
    }

    /**
     * Возвращает общее количество окон (RectButton и RoundButton), созданных с
     * помощью методов WindowFactory.
     *
     * @return
     */
    public static int getWindowCount() {
        return rectButtonCount + roundButtonCount;
    }

    /**
     * Устанавливает количество всех окон, созданных с помощью методов
     * WindowFactory, равным 0 (иными словами, реинициализирует фабрику).
     */
    public static void reset() {
        rectButtonCount = roundButtonCount = 0;
    }

}
