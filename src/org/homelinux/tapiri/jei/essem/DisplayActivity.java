package org.homelinux.tapiri.jei.essem;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Display;
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
        
        displayText = (AutoFitTextView) findViewById(R.id.displayText);
        
        // Get display sizes
        Display display = getWindowManager().getDefaultDisplay(); 
        @SuppressWarnings("deprecation")
		int width = display.getWidth();
        @SuppressWarnings("deprecation")
		int height = display.getHeight();
        
        // Set maximum text width accordingly
        displayText.setMaxTextSize(width);
        //displayText.setHorizontallyScrolling(true);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
