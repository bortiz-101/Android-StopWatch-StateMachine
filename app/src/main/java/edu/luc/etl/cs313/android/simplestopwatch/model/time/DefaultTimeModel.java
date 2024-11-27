package edu.luc.etl.cs313.android.simplestopwatch.model.time;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.*;
import java.util.function.BooleanSupplier;

/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;
    private int min;
    private int max;
    private int value;

    public DefaultTimeModel() {
        this(0,99);
    }
    public DefaultTimeModel(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min >= max");
        }
        this.min = min;
        this.max = max;
        this.value = min;
    }

    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        runningTime = (runningTime + SEC_PER_TICK) % SEC_PER_HOUR;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void setLaptime() {
        lapTime = runningTime;
    }

    @Override
    public int getLaptime() {
        return lapTime;
    }

    protected boolean dataInvariant() {
        return min <= value && value <= max;
    }

    @Override
    public void increment() {
        assertIfDebug(() -> dataInvariant() && !isFull());
        ++value;
        assertIfDebug(this::dataInvariant);
    }

    @Override
    public void decrement() {
        assertIfDebug(() -> dataInvariant() && !isEmpty());
        --value;
        assertIfDebug(this::dataInvariant);
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public boolean isFull() {
        return value >= max;
    }

    @Override
    public boolean isEmpty() {
        return value <= min;
    }

    protected void assertIfDebug(final BooleanSupplier p) {
    /*
    if (BuildConfig.DEBUG && !p.getAsBoolean()) {
      throw new AssertionError();

     */
    }
}
