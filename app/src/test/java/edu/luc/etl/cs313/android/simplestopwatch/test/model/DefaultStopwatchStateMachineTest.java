package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import edu.luc.etl.cs313.android.simplestopwatch.R;

import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;

/**
 * Concrete testcase subclass for the default stopwatch state machine
 * implementation.
 *
 * @author laufer
// * @see  ://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultStopwatchStateMachineTest extends AbstractStopwatchStateMachineTest {
    private DefaultStopwatchStateMachine stateMachine;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        setModel(new DefaultStopwatchStateMachine(getDependency(), getDependency()));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }


    /*
     * Tests if the state transition from IncrementingState to TimingState
     * is functional after 3 ticks while runtime is greater than 0.
     */
    @Test
    public void testIncrementingToTimingTransition() {
        stateMachine.toIncrementingState();
        for (int i = 0; i < 3; i++) {
            stateMachine.onTick();
        }
        assertEquals(R.string.TIMING, stateMachine.actionGetRuntime()); //OG: getCurrentState().getId();
    }

    /*
     * Tests if  the state transition to AlarmingState after runtime reaches 0 in
     * TimingState is functional.
     */
    @Test
    public void testTimingToAlarmingTransition() {
        stateMachine.toTimingState();
        while (stateMachine.actionGetRuntime() > 0) {
            stateMachine.onTick();
        }
        assertEquals(R.string.ALARMING, stateMachine.actionGetRuntime()); //OG: getCurrentState().getId();
    }
}