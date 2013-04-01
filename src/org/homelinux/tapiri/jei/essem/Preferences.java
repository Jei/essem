package org.homelinux.tapiri.jei.essem;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Preferences extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_PREF_DISPLAY = "displayPref";
	public static final String KEY_PREF_PADDING = "paddingPref";
	public static final String KEY_PREF_MAX_PADDING = "maxPaddingPref";
	public static final String KEY_PREF_SPELL = "spellPref";
	
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
		
		if (key.equals(KEY_PREF_PADDING)) {
			int paddingValueInt = 0;
			int maxPaddingInt = 0;
			try {
	    		paddingValueInt = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_PREF_PADDING, "0"));
	    		maxPaddingInt = Integer.parseInt(sharedPreferences.getString(Preferences.KEY_PREF_MAX_PADDING, "0"));
	    	} catch(NumberFormatException nfe) {
	    		Toast.makeText(getActivity(), getResources().getString(R.string.error_padding_parse), Toast.LENGTH_SHORT).show();
	    	}
			// Check if entered value is greater than maximum padding
			// If so, set it to maximum
			if (paddingValueInt > maxPaddingInt) {
				sharedPreferences.edit()
				    .putString(Preferences.KEY_PREF_PADDING, sharedPreferences.getString(Preferences.KEY_PREF_MAX_PADDING, "0"))
				    .commit();
				Toast.makeText(getActivity(), getResources().getString(R.string.warning_padding_too_large), Toast.LENGTH_SHORT).show();
			}
			
			String paddingValue = sharedPreferences.getString(key, "0");
			String maxPaddingValue = sharedPreferences.getString(KEY_PREF_MAX_PADDING, "0");
			String paddingDesc1 = getResources().getString(R.string.pref_padding_desc_part1);
			String paddingDesc2 = getResources().getString(R.string.pref_padding_desc_part2);
			
			preference.setSummary(paddingDesc1 + " " + paddingValue + " px" + paddingDesc2 + " " + maxPaddingValue + " px");
			
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
		// Get all the preferences from the default shared preferences
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String displayDesc = getResources().getString(R.string.pref_display_desc);
		String displayValue = sharedPreferences.getString(KEY_PREF_DISPLAY, "");
		String displayValueName = "";
		String paddingDesc1 = getResources().getString(R.string.pref_padding_desc_part1);
		String paddingDesc2 = getResources().getString(R.string.pref_padding_desc_part2);
		String paddingValue = sharedPreferences.getString(KEY_PREF_PADDING, "0");
		String maxPaddingValue = sharedPreferences.getString(KEY_PREF_MAX_PADDING, "0");
		
		if (displayValue.equals("auto")) {
			displayValueName = "Automatic";
		} else if (displayValue.equals("portrait")) {
			displayValueName = "Portrait";
		} else if (displayValue.equals("landscape")) {
			displayValueName = "Landscape";
		}
		
		// Change preferences summary according to current value
		findPreference(KEY_PREF_DISPLAY).setSummary(displayDesc + " " + displayValueName);
		findPreference(KEY_PREF_PADDING).setSummary(paddingDesc1 + " " + paddingValue + " px" + paddingDesc2 + " " + maxPaddingValue + " px");
		
	}

}