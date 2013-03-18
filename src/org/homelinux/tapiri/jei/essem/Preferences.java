package org.homelinux.tapiri.jei.essem;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class Preferences extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_PREF_DISPLAY = "displayPref";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		Preference preference = findPreference(key);
		
		if (key.equals(KEY_PREF_DISPLAY)) {
			String displayDesc = getResources().getString(R.string.pref_display_desc);
			String displayValue = sharedPreferences.getString(key, "");
			
			if (displayValue.equals("auto")) {
				
			} else if (displayValue.equals("portrait")) {
				
			} else if (displayValue.equals("landscape")) {
				
			}
			
			preference.setSummary(displayDesc + sharedPreferences.getString(key, ""));
			
		}
		
	}

}