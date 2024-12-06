package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.MAX_RUN_TIME;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;

/**
 * Functional tests for the stopwatch app.
 * Simulates user interactions and verifies full application behavior.
 */
public class FunctionalStopwatchTest {

    private final DefaultStopwatchStateMachine stateMachine;
    private StopwatchModelListener listener;

    public FunctionalStopwatchTest() {
        // Object initializations
        DefaultTimeModel timeModel = new DefaultTimeModel();
        DefaultClockModel clockModel = new DefaultClockModel();
        stateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel);

        // Set up a simple listener to avoid NullPointerException
        listener = new StopwatchModelListener() {
            @Override
            public void onTimeUpdate(int timeValue) {
                // No operation needed for this test
            }

            @Override
            public void onStateUpdate(int stateId) {
                // No operation needed for this test
            }

            @Override
            public void onAlarm() {
                // No operation needed for this test
            }

            @Override
            public void onStopAlarm() {
                // No operation needed for this test
            }

            @Override
            public void onBeep() {
                // No operation needed for this test
            }
        };

        // Set the listener in the state machine
        stateMachine.setModelListener(listener);
        stateMachine.actionInit();
    }

    /**
     * Simulates the full stopwatch flow from StoppedState to AlarmingState while
     * also testing the transitions through IncrementingState, TimingState, and back to StoppedState
     * after the alarm is stopped.
     */
    @Test
    public void testFullStopwatchFlow() throws NoSuchFieldException {
        stateMachine.toStoppedState();
        stateMachine.onStartStop(); // Increment
        for (int i = 0; i < 3; i++) {
            stateMachine.onTick(); // Increment for 3 seconds
        }

        assertEquals(R.string.TIMING, stateMachine.getCurrentStateId());

        while (stateMachine.actionGetRuntime() > 0) {
            stateMachine.onTick(); // Countdown
        }
        assertEquals(R.string.ALARMING, stateMachine.getCurrentStateId());

        stateMachine.onStartStop(); // Stop alarm
        assertEquals(R.string.STOPPED, stateMachine.getCurrentStateId());
    }

    /**
     * Tests that runtime cannot exceed MAX_RUN_TIME in IncrementingState.
     */
    @Test
    public void testRuntimeLimit() {
        stateMachine.toIncrementingState();
        for (int i = 0; i < 200; i++) { // Extreme increment
            stateMachine.onTick();
        }
        assertEquals(MAX_RUN_TIME, stateMachine.actionGetRuntime());
    }
}
