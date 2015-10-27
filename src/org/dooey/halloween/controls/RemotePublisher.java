package org.dooey.halloween.controls;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class RemotePublisher implements EventPublisher {
	private final Activity activity;
	private final View view;
	private final Remote remote;
	private final String command;

	public RemotePublisher(Activity activity, View view, Remote remote,
			String command) {
		this.activity = activity;
		this.view = view;
		this.remote = remote;
		this.command = command;
	}

	@Override
	public void publish(final byte[] blob) {
		new Thread(new Runnable() {
			public void run() {
				setEnabled(false);
				try {
					String message = remote.command(command, blob);
					if (message != null) {
						activity.runOnUiThread(new MakeToast(message));
					}
				} catch (RuntimeException e) {
					activity.runOnUiThread(new MakeToast(e));
				} finally {
					setEnabled(true);
				}
			}
		}).start();
	}

	@Override
	public void publish() {
		publish(null);
	}

	private void setEnabled(final boolean enabled) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				view.setEnabled(enabled);
			}
		});

	}

	class MakeToast implements Runnable {
		private final String message;
		private final int length;

		public MakeToast(RuntimeException e) {
			this.message = e.getLocalizedMessage();
			this.length = Toast.LENGTH_LONG;
		}

		public MakeToast(String message) {
			this.message = message;
			this.length = Toast.LENGTH_SHORT;
		}

		public void run() {
			Toast toast = Toast.makeText(activity.getApplicationContext(),
					message, length);
			toast.show();
		}
	}
}
