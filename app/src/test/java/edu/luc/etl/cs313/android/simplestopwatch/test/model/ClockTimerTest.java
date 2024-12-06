package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.TickListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

public abstract class ClockTimerTest {

    private StopwatchStateMachine model;

    private MockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new MockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    protected void setModel(final StopwatchStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setModelListener(dependency);
        this.model.actionInit();
    }

    protected MockDependency getDependency() {
        return dependency;
    }

    @Test
    public void testPreconditions() {
        assertEquals(R.string.STOPPED, dependency.getState());
    }

    @Test
    public void testScenarioRun() {
        assertTimeEquals(0);
        assertFalse(dependency.isStarted());
        model.onStartStop();
        assertTrue(dependency.isStarted());
        onTickRepeat(5);
        assertTimeEquals(5);
    }
    /**
     * Verifies the entire state transition cycle:
     * STOPPED -> INCREMENTING -> TIMING -> ALARMING -> STOPPED
     */
    @Test
    public void testStateTransitions() {
        // Initial state: STOPPED
        assertStateEquals(R.string.STOPPED);
        assertTimeEquals(0);

        // Transition to INCREMENTING
        model.onStartStop();
        assertStateEquals(R.string.INCREMENTING);
        assertTrue(dependency.isStarted());

        // Simulate ticks to reach MAX_RUN_TIME
        int maxTime = Constants.MAX_RUN_TIME;
        onTickRepeat(maxTime);
        assertTimeEquals(maxTime);
        assertStateEquals(R.string.INCREMENTING);

        // Transition to TIMING
        model.onStartStop();
        assertStateEquals(R.string.TIMING);
        assertTrue(dependency.isStarted());

        // Simulate ticks to decrement runtime to 0
        onTickRepeat(maxTime);
        assertTimeEquals(0);
        assertStateEquals(R.string.ALARMING);

        // Transition back to STOPPED
        model.onStartStop();
        assertStateEquals(R.string.STOPPED);
        assertTimeEquals(0);
        assertFalse(dependency.isStarted());
    }

    protected void onTickRepeat(final int n) {
        for (var i = 0; i < n; i++){
            model.onTick();
        }
    }

    protected void assertTimeEquals(final int t) {
        assertEquals(t, dependency.getTime());
    }

    protected void assertStateEquals(final int expectedState) {
        assertEquals(expectedState, dependency.getState());
    }
}



class MockDependency implements TimeModel, ClockModel, StopwatchModelListener {

    private int timeValue = -1, stateId = -1;

    private int runningTime = 0;

    private boolean started = false;

    public int getTime() {
        return timeValue;
    }

    public int getState() {
        return stateId;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void onTimeUpdate(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void onStateUpdate(final int stateId) {
        this.stateId = stateId;
    }

    @Override
    public void onAlarm() {

    }

    @Override
    public void onStopAlarm() {

    }

    @Override
    public void onBeep() {

    }

    @Override
    public void setTickListener(TickListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void incRuntime() {
        runningTime++;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void setRuntime(int runtime) {

    }

    @Override
    public void decRuntime() {

    }
}