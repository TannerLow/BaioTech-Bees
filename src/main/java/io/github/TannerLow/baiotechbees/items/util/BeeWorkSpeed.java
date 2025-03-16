package io.github.TannerLow.baiotechbees.items.util;

public enum BeeWorkSpeed {
    Slowest(0.3),
    Slower(0.6),
    Slow(0.8),
    Normal(1.0),
    Fast(1.2),
    Faster(1.4),
    Fastest(1.7);

    private final double _speed;

    BeeWorkSpeed(double _speed) {
        this._speed = _speed;
    }

    public double speed() {
        return _speed;
    }
}
