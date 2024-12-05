package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class StoppedState implements StopwatchState {

    public StoppedState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    private final StopwatchSMStateView sm;

    /* When the start/stop button is pressed transition to incrementing state
    * and start incrementing*/
    @Override
    public void onStartStop() {
        sm.toIncrementingState();
        sm.actionStart();
    }

    /* No onTick function */
    @Override
    public void onTick() {
        throw new UnsupportedOperationException("onTick");
    }


    @Override
    public void updateView() {
        sm.updateUIRuntime(); // updates UI with current RunTime
    }

    @Override
    public int getId() {
        return R.string.STOPPED; // updates State dialog box with current state
    }
}
