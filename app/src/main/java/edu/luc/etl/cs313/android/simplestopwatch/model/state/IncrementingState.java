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

     /* Increase runtime and update display view.
        Set to increasing state whenever the start-stop button is pressed.
     *  if the clock hits 99 move to the next state. */
     @Override
     public void onStartStop() {
         secPassed= -1;
         sm.actionInc();
         sm.toIncrementingState();
         if(sm.actionGetRuntime() == MAX_RUN_TIME){
             movetoNextState();
         }
     }

     /* Marks the process as started and
     increments the 'secPassed' counter */
     @Override
     public void onTick() {
         isStarted = true;
         secPassed++;

         /* If 3 sec has passed and if the run time > 0, transition to the next state */
         if(secPassed >= 3 && sm.actionGetRuntime() > 0  ){
             movetoNextState();
         }
     }

     /* If isStarted is true then
     stop the current action  */
     private void movetoNextState(){
         if(isStarted){
             sm.actionStop();
             isStarted = false;
         }
         secPassed = -1;         //Reset the time counter
         sm.toTimingState();     //Transition to timing state
         sm.actionBeep();        //play beep sound when timing state starts
         sm.actionStart();       //start the stopwatch

     }

    /* Updates UI view */
     @Override
     public void updateView() {
         sm.updateUIRuntime();
     }

     @Override
     public int getId() {
         return R.string.INCREMENTING; // updates State dialog box with current state
     }
}
