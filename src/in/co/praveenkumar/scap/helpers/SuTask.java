package in.co.praveenkumar.scap.helpers;

import java.io.IOException;
import java.io.OutputStream;

import android.os.AsyncTask;

public class SuTask extends AsyncTask<Boolean, Void, Boolean> {
	private final byte[] mCommand;

	public SuTask(byte[] command) {
		super();
		this.mCommand = command;
	}

	@Override
	protected Boolean doInBackground(Boolean... booleans) {
		try {
			/*
			 * -TODO- Add some delay before "su" command so the toast won't be
			 * recorded.
			 */
			Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream outputStream = sh.getOutputStream();
			outputStream.write(mCommand);
			outputStream.flush();
			outputStream.close();

			/*
			 * -TODO- Add a notification here
			 */

			sh.waitFor();
			return true;

		} catch (InterruptedException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

		return false;
	}

}
