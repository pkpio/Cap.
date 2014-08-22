package in.co.praveenkumar.scap.helpers;

import java.io.IOException;

import android.media.MediaRecorder;

public class AudioRecorder {
	MediaRecorder recorder = new MediaRecorder();

	public void startRecording() {
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile("/sdcard/test.mp3");
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.start();
	}

	public void stopRecording() {
		recorder.stop();
		recorder.release();
	}

}
