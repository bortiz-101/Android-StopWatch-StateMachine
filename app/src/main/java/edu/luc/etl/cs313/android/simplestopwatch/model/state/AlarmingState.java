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
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
    }
    @Override
    public void onTick() {
        sm.toAlarmingState();
        //sm.actionAlarm();
    }
    /* These can allow the user to stop the alarm and reset the stopwatch */
    @Override
    public void onIncrement() {
       // sm.actionAlarmStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void onDecrement() {
       // sm.actionAlarmStop();
        sm.actionReset();
        sm.toStoppedState();
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }

    private MediaPlayer mediaPlayer;


}
