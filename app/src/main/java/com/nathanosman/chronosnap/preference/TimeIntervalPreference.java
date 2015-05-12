package com.nathanosman.chronosnap.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nathanosman.chronosnap.R;

/**
 * @author sravan953
 *
 * Custom preference type for setting time interval, either in seconds or minutes.
 */
public class TimeIntervalPreference extends DialogPreference {
    Spinner spinner;
    EditText editText;

    public TimeIntervalPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.pref_time);
    }

    @Override
    protected void onBindDialogView(View view) {
        spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.pref_interval_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
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
        }
    }
}