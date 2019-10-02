package net.thumbtack.school.windows.managers;

import net.thumbtack.school.windows.v4.base.Window;
import net.thumbtack.school.windows.v4.base.WindowException;

public class NamedManager<T extends Window> extends Manager<Window> {

    private String name;

    public NamedManager(Window win, String name) throws WindowException {
        super(win);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
