// Copyright (C) 2013 Andrea Jonus
// See the LICENSE file for the full license notice
package org.homelinux.tapiri.jei.essem;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class DisplayActivity extends Activity implements DisplayFragment.OnFragmentInteractionListener {
	
	private static String TAG_DISPLAY_FRAGMENT = "display";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_display);
        
        // Add the display fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.main_layout, new DisplayFragment(), TAG_DISPLAY_FRAGMENT);
        ft.commit();
        
        // Set the default preference values at the first app run
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	DisplayFragment mDisplayFragment = (DisplayFragment) getFragmentManager().findFragmentByTag(TAG_DISPLAY_FRAGMENT);
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_settings:
        	// Hide soft keyboard
        	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        	FragmentTransaction ft = getFragmentManager().beginTransaction();
        	// If display fragment is visible, replace it with settings fragment
        	if (mDisplayFragment.isVisible()) {
        		ft.replace(R.id.main_layout, new Preferences());
                ft.addToBackStack(null);
                ft.commit();
        	}
            return super.onOptionsItemSelected(item);
        case R.id.menu_erase:
        	// If display fragment is visible, clear the text
        	if (mDisplayFragment.isVisible()) {
        		mDisplayFragment.clearText();
        	}
        	return super.onOptionsItemSelected(item);
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void onFragmentInteraction(Uri uri) {
    	
    }
    
}
