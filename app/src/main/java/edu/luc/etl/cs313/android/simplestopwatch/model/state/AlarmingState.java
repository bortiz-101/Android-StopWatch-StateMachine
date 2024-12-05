package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;


class AlarmingState implements StopwatchState {
    public AlarmingState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }
    private final StopwatchSMStateView sm;

    /*When the button is pressed when alarm is sounding
    * stop and reset the alarm*/
    @Override
    public void onStartStop() {
        sm.actionOnStopAlarm(); //stop the alarm
        sm.actionReset();       //reset the alarm
        sm.toStoppedState();    //transition alarm to stop state
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
