package edu.luc.etl.cs313.android.simplestopwatch.model.time;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.*;


/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int runningTime = 0;
    private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        if (runningTime < MAX_RUN_TIME){
            runningTime= (runningTime + SEC_PER_TICK) % SEC_PER_HOUR;
        }
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void setRuntime(int runtime) {
        runningTime = runtime;
    }

    @Override
    public void decRuntime() {
        runningTime = (runningTime - SEC_PER_TICK) % SEC_PER_HOUR;
    }

}
