package in.co.praveenkumar.scap.activities;

import in.co.praveenkumar.scap.R;
import in.co.praveenkumar.scap.helpers.DrawerActivity;
import in.co.praveenkumar.scap.helpers.SuTask;
import in.co.praveenkumar.scap.views.CustomCheckBox;

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
	SeekBar frameRateSeekBar;
	TextView frameRateTextView;
	EditText resolutionHeightTextView;
	EditText resolutionWidthTextView;
	CustomCheckBox audioCheckBox;
	CustomCheckBox touchCheckBox;
	CustomCheckBox durationCheckBox;
	EditText fileNameEditText;
	TextView recordBtnTextView;
	ImageView recordBtnImageView;

	int bitrate;
	String resolution;
	int timeLimit = 60 * 60;

	DisplayMetrics displayMetrics;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		displayMetrics = getResources().getDisplayMetrics();
		setUpViews();

	}

	private void setUpViews() {
		frameRateSeekBar = (SeekBar) findViewById(R.id.frame_rate_bar);
		frameRateTextView = (TextView) findViewById(R.id.frame_rate_text);
		resolutionHeightTextView = (EditText) findViewById(R.id.resolution_height);
		resolutionWidthTextView = (EditText) findViewById(R.id.resolution_width);
		audioCheckBox = (CustomCheckBox) findViewById(R.id.audio_checkbox);
		touchCheckBox = (CustomCheckBox) findViewById(R.id.touch_checkbox);
		durationCheckBox = (CustomCheckBox) findViewById(R.id.duration_checkbox);
		fileNameEditText = (EditText) findViewById(R.id.filename_value);
		recordBtnTextView = (TextView) findViewById(R.id.record_btn_text);
		recordBtnImageView = (ImageView) findViewById(R.id.record_btn_icon);

		// Set listeners and values
		frameRateSeekBar.setOnSeekBarChangeListener(new frameRateListener());
		Log.d("Test", displayMetrics.heightPixels + ", "
				+ displayMetrics.widthPixels);
		//resolutionHeightTextView.setText(displayMetrics.heightPixels);
		//resolutionWidthTextView.setText(displayMetrics.widthPixels);
	}

	private class frameRateListener implements OnSeekBarChangeListener {

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			bitrate = progress + 3;
			frameRateTextView.setText(bitrate + "Mbps");
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
		}

	}

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

			// stringBuilder.append(" --size ").append("1024x768");
			stringBuilder.append(" --bit-rate ").append("8000000");
			stringBuilder.append(" --time-limit ").append("30");

			// Location
			stringBuilder.append(" ").append("sdcard").append("/recording.mp4");
			Log.d("Launch", stringBuilder.toString());
			Log.d("RecordCommand", stringBuilder.toString());
			new SuTask(stringBuilder.toString().getBytes("ASCII")).execute();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
