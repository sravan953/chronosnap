package com.nathanosman.chronosnap.preference;

import android.content.Context;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.nathanosman.chronosnap.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Custom preference type for entering a time interval
 * @author sravan953
 *
 * Custom preference type for setting time interval, either in seconds or minutes.
 */
public class TimeIntervalPreference extends DialogPreference {

    // Predefined constants to simplify time conversion
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;

    /**
     * Storage for a time interval
     */
    private class Interval {
        public int mHours;
        public int mMinutes;
        public int mSeconds;
    }

    // References to the pickers
    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;
    private NumberPicker mSecondPicker;

    /**
     * Initialize the dialog with the custom layout
     */
    public TimeIntervalPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.pref_time);
    }

    /**
     * Obtain a human-readable representation of the provided value
     * @param value string value to convert
     * @return string representation
     */
    public String getSummary(String value) {

        // Obtain the current value as a long
        Interval interval = getInterval(Long.valueOf(value));

        // Construct the individual blocks of text that will be displayed
        List<String> parts = new ArrayList<>();
        Resources res = getContext().getResources();

        // Add hours (if present)
        if (interval.mHours != 0) {
            parts.add(res.getQuantityString(R.plurals.time_hour, interval.mHours,
                    interval.mHours));
        }

        // Add minutes (if present)
        if (interval.mMinutes != 0) {
            parts.add(res.getQuantityString(R.plurals.time_minute, interval.mMinutes,
                    interval.mMinutes));
        }

        // Add seconds (if present)
        if (interval.mSeconds != 0) {
            parts.add(res.getQuantityString(R.plurals.time_second, interval.mSeconds,
                    interval.mSeconds));
        }

        return TextUtils.join(", ", parts);
    }

    /**
     * Perform initialization of the view items
     */
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        // Obtain the current value
        long currentValue = Long.valueOf(getPersistedString(
                getContext().getString(R.string.pref_interval_default)));
        Interval interval = getInterval(currentValue);

<<<<<<< HEAD
        editText = (EditText)view.findViewById(R.id.timeInterval);

        /*
        To retrieve the already persisted (or default) values.
         */
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getContext());
        String intervalType = p.getString(getContext().getString(R.string.pref_interval_type_key), "seconds");
        long intervalMillis = Long.parseLong(p.getString(getContext().getString(R.string.pref_interval_key), getContext().getString(R.string.pref_interval_default)));
        int spinnerSelection = intervalType.equals("seconds") ? 0 : 1;
        intervalMillis = intervalType.equals("seconds") ? intervalMillis / 1000 : intervalMillis / (60 * 1000);

        /*
        To instantiate the preference with already persisted (or default) values.
         */
        spinner.setSelection(spinnerSelection);
        editText.setText(String.valueOf(intervalMillis));
=======
        // Obtain references to the pickers and initialize them with the correct value
        mHourPicker = (NumberPicker) view.findViewById(R.id.hourPicker);
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);
        mHourPicker.setValue(interval.mHours);

        mMinutePicker = (NumberPicker) view.findViewById(R.id.minutePicker);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setValue(interval.mMinutes);

        mSecondPicker = (NumberPicker) view.findViewById(R.id.secondPicker);
        mSecondPicker.setMinValue(0);
        mSecondPicker.setMaxValue(59);
        mSecondPicker.setValue(interval.mSeconds);
>>>>>>> origin/master
    }

    /**
     * Persist the value of the pickers
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
<<<<<<< HEAD
        if(positiveResult) {
            String type = spinner.getSelectedItem().toString();
            int time = Integer.parseInt(editText.getText().toString());
            if(type.equalsIgnoreCase("seconds"))
                time *= 1000;
            else
                time *= 60 * 1000;

            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getContext());
            p.edit().putString(getContext().getResources().getString(R.string.pref_interval_type_key), type).commit();
            p.edit().putString(getContext().getResources().getString(R.string.pref_interval_key), String.valueOf(time)).commit();
=======

        if (positiveResult) {

            // Get the current value as a string
            String currentValue = String.valueOf(mHourPicker.getValue() * HOUR +
                    mMinutePicker.getValue() * MINUTE + mSecondPicker.getValue() * SECOND);

            if (callChangeListener(currentValue)) {
                persistString(currentValue);
            }
>>>>>>> origin/master
        }
    }

    /**
     * Retrieve an interval based on the provided value
     * @param millis value in milliseconds
     * @return interval representing the provided value
     */
    private Interval getInterval(long millis) {

        // Create an interval to store the value
        Interval interval = new Interval();

        // Calculate the appropriate amounts for each component
        interval.mHours = (int) (millis / HOUR);
        millis -= interval.mHours * HOUR;
        interval.mMinutes = (int) (millis / MINUTE);
        millis -= interval.mMinutes * MINUTE;
        interval.mSeconds = (int) (millis / SECOND);

        return interval;
    }
}