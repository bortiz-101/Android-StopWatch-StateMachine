package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.EditText;

import java.io.IOException;
import java.util.Locale;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchModelListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteStopwatchModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.StopwatchModelFacade;


/**
 * A thin adapter component for the stopwatch.
 *
 * @author laufer
 */
public class StopwatchAdapter extends Activity implements StopwatchModelListener {

    private static String TAG = "stopwatch-android-activity";

    /**
     * The state-based dynamic model.
     */
    private StopwatchModelFacade model;
    private MediaPlayer mediaPlayer;

    protected void setModel(final StopwatchModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        //initial notification
        playNotification();
        // inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteStopwatchModelFacade());
        // inject dependency on this into model to register for UI updates
        model.setModelListener(this);
        findViewById(R.id.seconds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (model.isInitialState()){
                    showInputDialog();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.start();
    }

    // TODO remaining lifecycle methods

    /**
     * Updates the seconds and minutes in the UI.
     * @param time
     */
    public void onTimeUpdate(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = findViewById(R.id.seconds);
            //final TextView tvM = findViewById(R.id.minutes);
            final var locale = Locale.getDefault();
           // tvS.setText(String.format(locale,"%02d", time % Constants.SEC_PER_MIN));
            //tvM.setText(String.format(locale,"%02d", time / Constants.SEC_PER_MIN));
            tvS.setText(String.format(locale,"%02d", time ));
        });
    }

    /**
     * Updates the state name in the UI.
     * @param stateId
     */
    public void onStateUpdate(final int stateId) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView stateName = findViewById(R.id.stateName);
            stateName.setText(getString(stateId));
        });
    }

    // forward event listener methods to the model
    public void onStartStop(final View view) {
        model.onStartStop();
    }

    @Override
    public void onBeep(int resource, boolean isRepeat){
        playSound(R.raw.beep, false);
    }

    @Override
    public void onAlarm() {
        playSound(R.raw.alarm, true);
    }

    protected void playSound(int resource, boolean isRepeat){
        mediaPlayer = MediaPlayer.create(this, resource);
        mediaPlayer.setLooping(isRepeat);
        mediaPlayer.start();
    }

    @Override
    public void onStopAlarm() {
        if(mediaPlayer.isPlaying() && mediaPlayer != null){
            mediaPlayer.stop();
        }
    }



    @Override
    public void playNotification(){
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

    // show dialog to take initial value for the time
    private void showInputDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        EditText userInput = promptsView.findViewById(R.id.etInitialValue); // Input field for time.
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String entryValue = userInput.getText().toString();
                            int time = Integer.parseInt(entryValue);
                            model.initializeTime(time); // Initializes the countdown time.
                            onTimeUpdate(time); // Updates the UI with the new time.
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); // Logs an error if parsing fails.
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); // Cancels the dialog without any action.
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show(); // Displays the dialog to the user.
    }
}
