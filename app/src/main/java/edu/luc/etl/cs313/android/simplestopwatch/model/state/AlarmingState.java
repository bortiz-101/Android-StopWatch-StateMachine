package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import android.media.MediaPlayer;

class AlarmingState implements StopwatchState {
    public AlarmingState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }
    private final StopwatchSMStateView sm;

    @Override
    public void onStartStop() {
        sm.actionOnStopAlarm();
        sm.actionReset();
        sm.toStoppedState();
    }
    @Override
    public void onTick() {
        throw new UnsupportedOperationException("onTick");

    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }



}
