package org.homelinux.tapiri.jei.essem;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_PREF_DISPLAY = "displayPref";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		updateValues();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
		Preference preference = findPreference(key);
		
		if (key.equals(KEY_PREF_DISPLAY)) {
			String displayDesc = getResources().getString(R.string.pref_display_desc);
			String displayValue = sharedPreferences.getString(key, "");
			String displayValueName = "";
			
			// Change preference summary according to current value
			if (displayValue.equals("auto")) {
				displayValueName = getResources().getString(R.string.display_auto);
			} else if (displayValue.equals("portrait")) {
				displayValueName = getResources().getString(R.string.display_portrait);
			} else if (displayValue.equals("landscape")) {
				displayValueName = getResources().getString(R.string.display_landscape);
			}
			
			preference.setSummary(displayDesc + " " + displayValueName);
			
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}
	
	private void updateValues() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		
		String displayDesc = getResources().getString(R.string.pref_display_desc);
		String displayValue = sharedPreferences.getString(KEY_PREF_DISPLAY, "");
		String displayValueName = "";
		
		// Change preference summary according to current value
		if (displayValue.equals("auto")) {
			displayValueName = "Automatic";
		} else if (displayValue.equals("portrait")) {
			displayValueName = "Portrait";
		} else if (displayValue.equals("landscape")) {
			displayValueName = "Landscape";
		}
		
		findPreference(KEY_PREF_DISPLAY).setSummary(displayDesc + " " + displayValueName);
		
	}

}