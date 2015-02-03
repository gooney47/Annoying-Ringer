package oldenburg.philipp.ovenalerter;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.NumberPicker;


public class MainActivityActivity extends ActionBarActivity {

    private boolean running = false;
    private int secondsBetweenBeeps = 5 * 60;
    private Ringtone ringtone;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        String[] numbers = new String[60];
        for(int i=0; i<numbers.length; i++)
            numbers[i] = Integer.toString(i+1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(numbers);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        numberPicker.setValue(5);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                secondsBetweenBeeps = newVal * 60;
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int elapsedTime = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
                if (elapsedTime != 0 && elapsedTime % secondsBetweenBeeps == 0)
                    ringtone.play();
            }
        });

        final Button bt_toggleAlert = (Button) findViewById(R.id.bt_toggleAlert);
        bt_toggleAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    running = true;
                    bt_toggleAlert.setText(R.string.bt_stopAlert);
                } else {
                    chronometer.stop();
                    running = false;
                    bt_toggleAlert.setText(R.string.bt_startAlert);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
