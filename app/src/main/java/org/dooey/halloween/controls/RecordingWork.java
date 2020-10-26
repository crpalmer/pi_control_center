package org.dooey.halloween.controls;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class RecordingWork implements MomentaryWork {
	private final static String TAG = "RecordingWork";
	private static final int SAMPLING_RATE = 16000;

	private final AtomicBoolean stopRecording = new AtomicBoolean();

	private final EventPublisher publisher;

	public RecordingWork(EventPublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public void startWork() {
		stopRecording.set(false);
		new Thread(new Runnable() {
			public void run() {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				int bufferSize = AudioRecord.getMinBufferSize(SAMPLING_RATE,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT);
				Log.d(TAG, "bufferSize = " + bufferSize);
				AudioRecord recorder = new AudioRecord(
						MediaRecorder.AudioSource.MIC, SAMPLING_RATE,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT, bufferSize);
				try {
					byte[] data = new byte[bufferSize];
					Log.d(TAG, "startRecording");
					recorder.startRecording();
					long startMs = System.currentTimeMillis();
					int total_n = 0;
					while (!stopRecording.get()) {
						int n = recorder.read(data, 0, bufferSize);
						bytes.write(data, 0, n);
						total_n += n;
					}
					Log.d(TAG,
							"captured " + total_n + " bytes in "
									+ (System.currentTimeMillis() - startMs)
									+ " ms");
				} finally {
					if (recorder.getState() == 1) {
						recorder.stop();
					}
					recorder.release();
				}
				Log.d(TAG, "publishing " + bytes.size() + " bytes");

				publisher.publish(bytes.toByteArray());
			}
		}).start();
	}

	@Override
	public void completeWork() {
		stopRecording.set(true);
	}
}