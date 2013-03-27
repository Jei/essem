package org.homelinux.tapiri.jei.essem;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class DisplayActivity extends FragmentActivity implements DisplayFragment.OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_display);
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_settings:
        	getFragmentManager().beginTransaction()
        		.replace(R.id.displayFragment, new Preferences())
        		.commit();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void onFragmentInteraction(Uri uri) {
    	
    }
    
}
