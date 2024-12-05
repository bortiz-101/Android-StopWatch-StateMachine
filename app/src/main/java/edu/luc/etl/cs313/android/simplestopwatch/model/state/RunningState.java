package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class RunningState implements StopwatchState {

    public RunningState(final StopwatchSMStateView sm) {
        this.sm = sm;
    }

    private final StopwatchSMStateView sm;

    /* If the button is no longer being pressed after 3 sec
    * stops current process and transitions to stopped state*/
    @Override
    public void onStartStop() {
        sm.actionStop();
        sm.toStoppedState();
    }

    /* Increments timer */
    @Override
    public void onTick() {
        sm.actionInc();
        sm.toRunningState();
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime(); // updates UI with current runtime
    }

    @Override
    public int getId() {
        return R.string.RUNNING; // updates State dialog box with current state
    }
}
