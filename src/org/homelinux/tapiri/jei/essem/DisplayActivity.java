package org.homelinux.tapiri.jei.essem;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class DisplayActivity extends Activity {
	
	private AutoFitTextView displayText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_display);
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        displayText = (AutoFitTextView) findViewById(R.id.displayText);
        
        // Get display sizes
        Rect display = new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(display);
        int width = display.width();
        int height = display.height();
        
        // Set maximum text size accordingly
        displayText.setMaxTextSize(Math.max(width, height));
        displayText.setText(R.string.default_message);
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
        		.replace(android.R.id.content, new Preferences())
        		.commit();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}
