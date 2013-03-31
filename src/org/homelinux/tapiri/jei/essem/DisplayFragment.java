package org.homelinux.tapiri.jei.essem;

import android.app.Activity;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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
    	
    	if (displayMode.equals("auto")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		} else if (displayMode.equals("portrait")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else if (displayMode.equals("landscape")) {
			this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
    }
	
}
