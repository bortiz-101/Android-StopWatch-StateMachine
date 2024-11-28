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

     @Override
     public void onStartStop() {
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
     }
 }

