package edu.luc.etl.cs313.android.simplestopwatch.test.model;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;

/**
 * Concrete testcase subclass for the default time model implementation.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultTimeModelTest extends AbstractTimeModelTest {

    @Before
    public void setUp() throws Exception {
        setModel(new DefaultTimeModel());
    }

    @After
    public void tearDown() throws Exception {
        setModel(null);
    }

    /**
    * Tests that the runtime increments by 1 second when incRuntime() is called,
    * and does not exceed MAX_RUN_TIME.
    */
    @Test
    public void testIncrementRuntime() {
        model.setRuntime(58);
        model.incRuntime();
        assertEquals(59, model.getRuntime());
    }

    /**
    * Tests that resetRuntime() sets the runtime back to 0.
    */
    @Test
    public void testResetRuntime() {
        model.setRuntime(30);
        model.resetRuntime();
        assertEquals(0, model.getRuntime());
    }

    /**
    * Tests that decrementing runtime does not cause underflow.
    */
    @Test
    public void testDecrementRuntime() {
        model.setRuntime(1);
        model.decRuntime();
        assertEquals(0, model.getRuntime());
    }
}
