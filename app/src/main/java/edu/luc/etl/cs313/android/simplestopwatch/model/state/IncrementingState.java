package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.MAX_RUN_TIME;

 class IncrementingState implements StopwatchState {
     public IncrementingState(final StopwatchSMStateView sm) {
         this.sm = sm;
     }
     private final StopwatchSMStateView sm;
     private int   secPassed = -1;
     private boolean isStarted = false;

     @Override
     public void onStartStop() {
         secPassed= -1;
         sm.actionInc();
        sm.toIncrementingState();
         if(sm.actionGetRuntime() == MAX_RUN_TIME){
             movetoNextState();
         }
     }
     @Override
     public void onTick() {
         isStarted = true;
         secPassed++;

         if(secPassed >= 3 && sm.actionGetRuntime() > 0  ){
             movetoNextState();
         }
     }

     private void movetoNextState(){
         if(isStarted){
             sm.actionStop();
             isStarted = false;
         }
         secPassed = -1;
         sm.toTimingState();
         sm.actionBeep();
         sm.actionStart();

     }


     @Override
     public void updateView() {
         sm.updateUIRuntime();
     }

     @Override
     public int getId() {
         return R.string.INCREMENTING;
     }
}
