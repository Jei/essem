// Copyright (C) 2013 Andrea Jonus
// See the LICENSE file for the full license notice
package org.homelinux.tapiri.jei.essem;

import android.app.Activity;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link DisplayFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link DisplayFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class DisplayFragment extends Fragment {
	
	private AutoFitTextView displayText;
	private boolean isFirstTimeFocused;

	private OnFragmentInteractionListener mListener;

	public DisplayFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		displayText = (AutoFitTextView) getView().findViewById(R.id.displayText);
        
        // Get display sizes
        Rect display = new Rect();
        Window window= getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(display);
        int width = display.width();
        int height = display.height();
        
        // Set maximum text size accordingly
        displayText.setMaxTextSize(Math.max(width, height));
        displayText.setText(R.string.default_message);
        
        // Set a focus change listener to erase text at first focus
        displayText.setSelected(false);
        displayText.clearFocus();
        isFirstTimeFocused = true;
        displayText.setOnFocusChangeListener(new OnFocusChangeListener() {
        	@Override
        	public void onFocusChange(View v, boolean hasFocus) {
        	    if(hasFocus && isFirstTimeFocused){
        	        clearText();
        	        isFirstTimeFocused = false;
        	        // Show input method
        	        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        	        imm.showSoftInput(displayText, 0);
        	    }
        	}
        });
		
        // Set maximum padding in the shared preferences
        // Max padding is based on minimum font size and screen size
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        int maxPadding = (Math.min(width, height))/2 - Math.round(displayText.getMinTextSize());
        prefs.edit().putString(Preferences.KEY_PREF_MAX_PADDING, "" + maxPadding).commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_display, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateFromPreferences();
	}
	
	// Get preferences values and update UI accordingly
    private void updateFromPreferences() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
    	String displayMode = prefs.getString(Preferences.KEY_PREF_DISPLAY, "auto");
    	boolean spellcheckEnabled = prefs.getBoolean(Preferences.KEY_PREF_SPELL, false);
    	int padding = 0;
    	try {
    		padding = Integer.parseInt(prefs.getString(Preferences.KEY_PREF_PADDING, "0"));
    	} catch(NumberFormatException nfe) {
    		Toast.makeText(getActivity(), getResources().getString(R.string.error_padding_parse), Toast.LENGTH_SHORT).show();
    	}
    	
    	// Set screen orientation
    	if (displayMode.equals("auto")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else if (displayMode.equals("portrait")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else if (displayMode.equals("landscape")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
    	
    	// TODO: Set spellcheck
    	/*
    	if (spellcheckEnabled) {
    		displayText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
    	} else {
    		displayText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    	}
    	displayText.setSingleLine(false);
    	*/
    	
    	// Set padding
    	displayText.setPadding(padding, padding, padding, padding);
    }
    
    // Utility method to erase all the text from the AutoFitTextView
    public void clearText() {
    	displayText.setText("");
    }
	
}
