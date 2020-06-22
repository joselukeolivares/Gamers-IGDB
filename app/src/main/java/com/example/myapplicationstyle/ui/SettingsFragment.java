package com.example.myapplicationstyle.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.myapplicationstyle.R;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        androidx.preference.Preference p=findPreference(key);
        if(null!=p){
            if(!(p instanceof CheckBoxPreference)){
                String value=sharedPreferences.getString(key,"");
                Log.i(this.getClass().getName(),"onSharedPreferenceChanged");
                setPreferenceSummary(p,value);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.pref_games);



        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
        int count=preferenceScreen.getPreferenceCount();

        for(int i=0;i<count;i++){
            androidx.preference.Preference p=preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value=sharedPreferences.getString(p.getKey(),getString(R.string.pref_platform_label_ps4));
                setPreferenceSummary(p,value);
            }
        }





    }


    public void setPreferenceSummary(androidx.preference.Preference p,String value){
            if(p instanceof ListPreference){
                ListPreference listPreference=(ListPreference) p;
                int prefIndex=listPreference.findIndexOfValue(value);
                if(prefIndex>=0){
                    listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            }
    }
}
