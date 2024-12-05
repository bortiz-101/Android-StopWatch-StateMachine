package edu.luc.etl.cs313.android.simplestopwatch.common;

/** A Timeable component.
 *
 */

public interface Timeable {
    void initializeTime(int value);
    boolean isInitialState ();
}
