package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * A startable component.
 *
 * @author laufer
 */
public interface Startable {
    void start();
    void alarm();
    void initializeTime(int value);
    boolean isInitialState();
}
