package pl.sda.sdaAlarm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAG_SETTINGS_DIALOG = "SettingsDialog";

    @BindView(R.id.mainActivityTimePicker)
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        timePicker.setIs24HourView(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                openSettings();
                break;
            case R.id.menu_item_test_alarm:
                openAlarm();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAlarm() {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    private void openSettings() {
        SettingsDialogFragment settingsDialogFragment = SettingsDialogFragment.newInstance();
        settingsDialogFragment.show(getSupportFragmentManager(), TAG_SETTINGS_DIALOG);
    }

    @OnClick(R.id.mainActivityConfirmButton)
    public void confirmAlarm() {
        int hour = getHour();
        int minutes = getMinutes();
        Calendar calendar = getCalendar(hour, minutes);
        Log.d(TAG, String.valueOf(calendar.getTimeInMillis()));
    }

    @NonNull
    private Calendar getCalendar(int hour, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        return calendar;
    }

    private int getMinutes() {
        int minutes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minutes = timePicker.getMinute();
        } else {
            minutes = timePicker.getCurrentMinute();
        }
        return minutes;
    }

    private int getHour() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? timePicker.getHour() : timePicker.getCurrentHour();
    }
}
