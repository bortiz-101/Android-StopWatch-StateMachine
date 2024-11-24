package edu.luc.etl.cs313.android.simplestopwatch.model.clock;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;


/**
 * An implementation of the internal clock.
 *
 * @author laufer
 */
public class DefaultClockModel implements ClockModel {

    // TODO make accurate by keeping track of partial seconds when canceled etc.

    private Timer timer;

    private TickListener listener;

    @Override
    public void setTickListener(final TickListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        timer = new Timer();

        // The clock model runs onTick every 1000 milliseconds
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // fire event
                listener.onTick();
            }
        }, /*initial delay*/ 1000, /*periodic delay*/ 1000);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    @Override
    public void alarm(){
        final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final Context context = getApplicationContext();

        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void stopAlarm() {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.stop();
        }
        catch (final Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increment() {
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // fire event
                listener.onTick();
            }
        }, /*initial delay*/ 1000, /*periodic delay*/ 1000);

    }
}