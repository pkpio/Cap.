package in.co.praveenkumar.scap.activities;

import in.co.praveenkumar.scap.R;
import in.co.praveenkumar.scap.helpers.SuTask;
import in.co.praveenkumar.scap.views.CustomCheckBox;
import in.co.praveenkumar.scap.views.CustomCheckBox.OnCheckBoxStateChangeListener;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	SeekBar bitrateSeekBar;
	TextView bitrateTextView;
	EditText resolutionHeightTextView;
	EditText resolutionWidthTextView;
	CustomCheckBox audioCheckBox;
	CustomCheckBox touchCheckBox;
	CustomCheckBox durationCheckBox;
	EditText fileNameEditText;
	TextView recordBtnTextView;
	ImageView recordBtnImageView;

	int bitrate = 7;
	String resolution;
	int timeLimit = 30;// 60 * 60;
	String fileName;
	Boolean isAudio = false;
	Boolean isTouch = false;
	Boolean isTimelimit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.recording_layout);
		super.onCreate(savedInstanceState);
		setUpViews();
	}

	private void setUpViews() {
		bitrateSeekBar = (SeekBar) findViewById(R.id.frame_rate_bar);
		bitrateTextView = (TextView) findViewById(R.id.frame_rate_text);
		resolutionHeightTextView = (EditText) findViewById(R.id.resolution_height);
		resolutionWidthTextView = (EditText) findViewById(R.id.resolution_width);
		audioCheckBox = (CustomCheckBox) findViewById(R.id.audio_checkbox);
		touchCheckBox = (CustomCheckBox) findViewById(R.id.touch_checkbox);
		durationCheckBox = (CustomCheckBox) findViewById(R.id.duration_checkbox);
		fileNameEditText = (EditText) findViewById(R.id.filename_value);
		recordBtnTextView = (TextView) findViewById(R.id.record_btn_text);
		recordBtnImageView = (ImageView) findViewById(R.id.record_btn_icon);

		// Set listeners
		bitrateSeekBar.setOnSeekBarChangeListener(bitrateListener);
		audioCheckBox.setOnCheckBoxStateChangeListener(audioListener);
		touchCheckBox.setOnCheckBoxStateChangeListener(touchpointListener);
		durationCheckBox.setOnCheckBoxStateChangeListener(durationListener);

		// Set values
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		resolutionHeightTextView.setText(displayMetrics.heightPixels + "");
		resolutionWidthTextView.setText(displayMetrics.widthPixels + "");
		bitrateSeekBar.setProgress(bitrate);
	}

	OnCheckBoxStateChangeListener audioListener = new OnCheckBoxStateChangeListener() {

		@Override
		public void OnCheckBoxStateChanged(boolean isChecked) {
			isAudio = isChecked;
		}
	};

	OnCheckBoxStateChangeListener touchpointListener = new OnCheckBoxStateChangeListener() {

		@Override
		public void OnCheckBoxStateChanged(boolean isChecked) {
			isTouch = isChecked;
			Settings.System.putInt(getContentResolver(), "show_touches",
					isChecked ? 1 : 0);

		}
	};

	OnCheckBoxStateChangeListener durationListener = new OnCheckBoxStateChangeListener() {

		@Override
		public void OnCheckBoxStateChanged(boolean isChecked) {
			// TODO Auto-generated method stub
			isTimelimit = isChecked;
			Log.d("Stater", "State changed bro.");
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getFragmentManager(), "datePicker");

		}
	};

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
		}
	}

	OnSeekBarChangeListener bitrateListener = new OnSeekBarChangeListener() {

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			bitrate = progress + 3;
			bitrateTextView.setText(bitrate + "Mbps");
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("SdCardPath")
	public void recordButtonClick(View v) {
		try {
			StringBuilder stringBuilder = new StringBuilder(
					"/system/bin/screenrecord");

			// Get values
			resolution = resolutionWidthTextView.getText().toString() + "x"
					+ resolutionHeightTextView.getText().toString();
			fileName = fileNameEditText.getText().toString();
			if (fileName.contentEquals(""))
				fileName = "recording";

			stringBuilder.append(" --size ").append(resolution);
			stringBuilder.append(" --bit-rate ").append(bitrate * 1000000);
			stringBuilder.append(" --time-limit ").append(timeLimit);

			// Location
			stringBuilder.append(" ").append("/sdcard/")
					.append(fileName + ".mp4");
			Log.d("Launch", stringBuilder.toString());
			Log.d("RecordCommand", stringBuilder.toString());
			new SuTask(stringBuilder.toString().getBytes("ASCII")).execute();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
