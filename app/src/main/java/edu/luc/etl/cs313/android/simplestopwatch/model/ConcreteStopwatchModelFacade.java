package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteStopwatchModelFacade implements StopwatchModelFacade {

    private final StopwatchStateMachine stateMachine;

    private final ClockModel clockModel;

    private final TimeModel timeModel;

    // sets up all necessary objects
    public ConcreteStopwatchModelFacade() {
        timeModel = new DefaultTimeModel();
        clockModel = new DefaultClockModel();
        stateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel);
        clockModel.setTickListener(stateMachine);
    }

    //initializes the state machine
    @Override
    public void start() {
        stateMachine.actionInit(); // sends to StoppedState and resets timer
    }

    // initializes the statemachine listener for StopwatchUIListener, TickListener,
    // StopwatchModelSource, StopwatchSMStateView
    @Override
    public void setModelListener(final StopwatchModelListener listener) {
        stateMachine.setModelListener(listener);
    }

    // applies UI listener for button presses to the statemachine
    @Override
    public void onStartStop() {
        stateMachine.onStartStop();
    }

    // Initializes running time
    @Override
    public void initializeTime(int value) {
        stateMachine.actionSetRuntime(value);

    }

    // Sets the initial state of the state machine to stopped
    @Override
    public boolean isInitialState() {
        return stateMachine.isStopped();
    }

}
