package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.MAX_RUN_TIME;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Functional tests for the stopwatch app.
 * Simulates user interactions and verifies full application behavior.
 */
public class FunctionalStopwatchTest {

    private final DefaultStopwatchStateMachine stateMachine;

    public FunctionalStopwatchTest() {
        // Object initializations
        DefaultTimeModel timeModel = new DefaultTimeModel();
        DefaultClockModel clockModel = new DefaultClockModel();

        // Initialize the state machine with the clock and time models
        stateMachine = new DefaultStopwatchStateMachine(timeModel, clockModel);

        // Set up the StopwatchModelListener for the state machine
        StopwatchModelListener listener = new StopwatchModelListener() {
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

        // Assign the listener to the state machine
        stateMachine.setModelListener(listener);
        // Initialize the state machine
        stateMachine.actionInit();
    }

    /**
     * Tests that the state machine sets up properly.
     */
    @Test
    public void testActivityTestCaseSetUpProperly() {
        stateMachine.actionInit();
        assertEquals(R.string.STOPPED, stateMachine.getCurrentStateId());
    }

    @Test
    public void testButtonPressIncUntilFull() {
        // Simulate extreme increments to ensure it does not exceed MAX_RUN_TIME
        stateMachine.toIncrementingState();
        for (int i = 0; i < 200; i++) {
            stateMachine.actionInc();
        }
        assertEquals(R.string.INCREMENTING, stateMachine.getCurrentStateId());
        assertEquals(MAX_RUN_TIME, stateMachine.actionGetRuntime());
    }

    @Test
    public void testInitialState() {
        assertEquals(0, stateMachine.actionGetRuntime());
    }

    @Test
    public void testButtonIncrement() {
        stateMachine.actionInc();
        assertEquals(1, stateMachine.actionGetRuntime());
    }

    @Test
    public void testThreeSecTimeOut() {
        stateMachine.toIncrementingState();
        for (int i = 0; i < 3; i++) {
            stateMachine.actionInc(); // Increment for 3 seconds
        }
        stateMachine.toRunningState();
        assertEquals(R.string.RUNNING, stateMachine.getCurrentStateId());
    }

    @Test
    public void testStateWhenFull() {
        stateMachine.actionSetRuntime(99);
        stateMachine.toRunningState();
        assertEquals(R.string.RUNNING, stateMachine.getCurrentStateId());
    }

    @Test
    public void testDecrementValue() {
        stateMachine.actionSetRuntime(5);
        stateMachine.actionDec();
        stateMachine.actionDec();
        assertEquals(3, stateMachine.actionGetRuntime());
    }
    @Test
    public void testCancelButton() {
        stateMachine.actionSetRuntime(3);
        stateMachine.actionDec();
        stateMachine.actionDec();
        assertEquals(1, stateMachine.actionGetRuntime());
    }
    @Test
    public void testSoundStop() {
        stateMachine.actionSetRuntime(1);
        stateMachine.actionDec();
        assertEquals(0, stateMachine.actionGetRuntime());
    }


    /**
     * Simulates the full stopwatch flow from StoppedState to AlarmingState while
     * also testing the transitions through IncrementingState, TimingState, and back to StoppedState
     * after the alarm is stopped.
     */
    @Test
    public void testFullStopwatchFlow() throws NoSuchFieldException {
        // Set the state machine to the StoppedState
        stateMachine.toStoppedState();

        // Simulate pressing the start button to increment the runtime
        stateMachine.onStartStop();
        for (int i = 0; i < 3; i++) {
            stateMachine.onTick(); // Increment for 3 seconds
        }

        // Assert that the current state is IncrementingState
        assertEquals(R.string.INCREMENTING, stateMachine.getCurrentStateId());

        // Simulate waiting for 5 seconds (tick down)
        for (int i = 0; i < 5; i++) {
            stateMachine.onTick(); // Countdown ticks
        }
        stateMachine.toRunningState();

        // Assert that the state has transitioned to RunningState
        assertEquals(R.string.RUNNING, stateMachine.getCurrentStateId());

        // Stop the stopwatch
        stateMachine.onStartStop();

        // Assert that the state is back to IncrementingState
        assertEquals(R.string.STOPPED, stateMachine.getCurrentStateId());
    }

    /**
     * Tests that runtime cannot exceed MAX_RUN_TIME in IncrementingState.
     */
    @Test
    public void testRuntimeLimit() {
        // Set the state to IncrementingState
        stateMachine.toIncrementingState();

        // Simulate extreme increments to ensure it does not exceed MAX_RUN_TIME
        for (int i = 0; i < 200; i++) {
            stateMachine.actionInc();
        }

        // Assert the runtime does not exceed MAX_RUN_TIME
        assertEquals(MAX_RUN_TIME, stateMachine.actionGetRuntime());
    }
}
