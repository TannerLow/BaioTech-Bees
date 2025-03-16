package io.github.TannerLow.baiotechbees.items.util;

public enum Lifespan {
    Blink(2),
    Shortest(10),
    Shorter(20),
    Short(30),
    Shortened(35),
    Normal(40),
    Elongated(45),
    Long(50),
    Longer(60),
    Longest(70),
    Eon(600),
    IMMORTAL(Integer.MAX_VALUE);

    private final int _beeticks;

    Lifespan(int _beeticks) {
        this._beeticks = _beeticks;
    }

    public int beeticks() {
        return _beeticks;
    }
}
