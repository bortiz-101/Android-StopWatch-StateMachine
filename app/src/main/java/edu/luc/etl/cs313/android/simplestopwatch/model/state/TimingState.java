package edu.luc.etl.cs313.android.simplestopwatch.model.state;
import edu.luc.etl.cs313.android.simplestopwatch.R;

 class TimingState implements StopwatchState {
        private final StopwatchSMStateView sm;
        public TimingState(final StopwatchSMStateView sm){
            this.sm = sm;
        }

     @Override
     public void updateView() {
         sm.updateUIRuntime();
     }

     @Override
     public int getId() {
         return R.string.TIMING;
     }

     /* When timer reaches 0, stop and reset the timer and sound alarm
     * Otherwise decrease the timer and transition to timing state */
     @Override
     public void onTick() {
        if(sm.actionGetRuntime() == 0){
            sm.actionStop();
            sm.actionReset();
            sm.toAlarmingState();
            sm.actionOnAlarm();
        } else {
            sm.actionDec();
            sm.toTimingState();
        }
     }

     /* Stops and resets stopwatch */
     @Override
     public void onStartStop() {
        sm.actionStop();        //Stop current action
        sm.actionReset();       //Reset the timer
        sm.toStoppedState();    //Transition to stopped state
     }
 }

