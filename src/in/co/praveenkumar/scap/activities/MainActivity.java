package in.co.praveenkumar.scap.activities;

import in.co.praveenkumar.scap.R;
import in.co.praveenkumar.scap.helpers.DrawerActivity;
import in.co.praveenkumar.scap.helpers.SuTask;
import in.co.praveenkumar.scap.views.CustomCheckBox;
import in.co.praveenkumar.scap.views.CustomCheckBox.OnCheckBoxStateChangeListener;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends DrawerActivity {
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
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
			// TODO Auto-generated method stub
			Log.d("Stater", "State changed bro.");

		}
	};

	OnCheckBoxStateChangeListener touchpointListener = new OnCheckBoxStateChangeListener() {

		@Override
		public void OnCheckBoxStateChanged(boolean isChecked) {
			// TODO Auto-generated method stub
			Log.d("Stater", "State changed bro.");

		}
	};

	OnCheckBoxStateChangeListener durationListener = new OnCheckBoxStateChangeListener() {

		@Override
		public void OnCheckBoxStateChanged(boolean isChecked) {
			// TODO Auto-generated method stub
			Log.d("Stater", "State changed bro.");

		}
	};

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void recordButtonClick(View v) {
		try {
			StringBuilder stringBuilder = new StringBuilder(
					"/system/bin/screenrecord");

			// Get values
			resolution = resolutionWidthTextView.getText().toString() + "x"
					+ resolutionHeightTextView.getText().toString();
			fileName = fileNameEditText.getText().toString();

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
