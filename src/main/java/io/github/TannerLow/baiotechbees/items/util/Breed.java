package io.github.TannerLow.baiotechbees.items.util;

public enum Breed {
    Forest((short)0, (byte)3, Lifespan.Shorter.beeticks(), BeeWorkSpeed.Slowest.speed()),
    Meadows((short)1, (byte)2, Lifespan.Shorter.beeticks(), BeeWorkSpeed.Slowest.speed()),
    Common((short)2, (byte)2, Lifespan.Shorter.beeticks(), BeeWorkSpeed.Slower.speed()),
    Cultivated((short)3, (byte)2, Lifespan.Shortest.beeticks(), BeeWorkSpeed.Fast.speed()),
    Noble((short)4, (byte)2, Lifespan.Short.beeticks(), BeeWorkSpeed.Slower.speed()),
    Diligent((short)5, (byte)2, Lifespan.Shortened.beeticks(), BeeWorkSpeed.Slower.speed()),
    Majestic((short)6, (byte)4, Lifespan.Short.beeticks(), BeeWorkSpeed.Normal.speed()),
    Unweary((short)7, (byte)2, Lifespan.Shortened.beeticks(), BeeWorkSpeed.Normal.speed());

    private final short _id;
    private final byte _fertility;
    private final int _lifespan;
    private final double _speed;

    Breed(short _id, byte _fertility, int _lifespan, double _speed) {
        this._id = _id;
        this._fertility = _fertility;
        this._lifespan = _lifespan;
        this._speed = _speed;
    }

    public short id() {
        return _id;
    }

    public byte fertility() {
        return _fertility;
    }

    public int lifespan() {
        return _lifespan;
    }

    public double speed() {
        return _speed;
    }
}
