package in.co.praveenkumar.scap.activities;

import in.co.praveenkumar.scap.R;
import in.co.praveenkumar.scap.helpers.DrawerActivity;
import in.co.praveenkumar.scap.helpers.SuTask;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends DrawerActivity {
	StringBuilder stringBuilder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		stringBuilder = new StringBuilder("/system/bin/screenrecord");

		// stringBuilder.append(" --size ").append("1024x768");
		stringBuilder.append(" --bit-rate ").append("8000000");
		stringBuilder.append(" --time-limit ").append("30");

		// Location
		stringBuilder.append(" ")
				.append(Environment.getExternalStorageDirectory().toString())
				.append("/recording.mp4");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void record(View v) {
		try {
			new SuTask(stringBuilder.toString().getBytes("ASCII")).execute();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
