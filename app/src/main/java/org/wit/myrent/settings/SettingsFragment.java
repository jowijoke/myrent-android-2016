package org.wit.myrent.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import org.wit.myrent.R;

/**
 * Created by User on 24/10/2016.
 */

public class SettingsFragment extends PreferenceFragment {
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onStart() {
        super.onStart();
        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }
}