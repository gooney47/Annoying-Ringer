package oldenburg.philipp.ovenalerter;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class MainActivityActivity extends ActionBarActivity {

    private boolean running = false;
    private int secondsBetweenBeeps = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                int elapsedTime = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
                if( elapsedTime != 0 && elapsedTime % secondsBetweenBeeps == 0)
                    Log.d("debug", "" + elapsedTime);
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
