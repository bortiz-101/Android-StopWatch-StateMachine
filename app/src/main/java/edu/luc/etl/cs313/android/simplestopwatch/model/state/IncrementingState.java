package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

 class IncrementingState implements StopwatchState {
     public IncrementingState(final StopwatchSMStateView sm) {
         this.sm = sm;
     }
     private final StopwatchSMStateView sm;

     @Override
     public void onStartStop() {
         sm.toIncrementingState();
         sm.actionInc();
     }
     @Override
     public void onTick() {
         throw new UnsupportedOperationException("onTick");
     }

     @Override
     public void onIncrement() {

     }

     @Override
     public void onDecrement() {

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
