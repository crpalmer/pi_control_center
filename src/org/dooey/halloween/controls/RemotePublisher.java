package org.dooey.halloween.controls;

import android.app.Activity;
import android.widget.Toast;

public class RemotePublisher implements EventPublisher {
	private final Activity activity;
	private final Remote remote;
	private final String command;

	public RemotePublisher(Activity activity, Remote remote, String command) {
		this.activity = activity;
		this.remote = remote;
		this.command = command;
	}

	@Override
	public void publish(final byte[] blob) {
		new Thread(new Runnable() {
			public void run() {
				try {
					remote.command(command, blob);
				} catch (RuntimeException e) {
					activity.runOnUiThread(new MakeToast(e));
				}
			}
		}).start();
	}

	@Override
	public void publish() {
		publish(null);
	}

	class MakeToast implements Runnable {
		private final RuntimeException e;

		public MakeToast(RuntimeException e) {
			this.e = e;
		}

		public void run() {
			Toast toast = Toast.makeText(activity.getApplicationContext(),
					e.getLocalizedMessage(), Toast.LENGTH_LONG);
			toast.show();
		}
	}
}
