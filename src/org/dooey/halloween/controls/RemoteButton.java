package org.dooey.halloween.controls;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RemoteButton implements OnClickListener {
	private final Activity activity;
	private final Remote remote;
	private final String command;

	public RemoteButton(Activity activity, Button button, Remote remote,
			String command) {
		button.setOnClickListener(this);
		this.activity = activity;
		this.remote = remote;
		this.command = command;
	}

	public void onClick(View unused) {
		new Thread(new Runnable() {
			public void run() {
				try {
					remote.command(command);
				} catch (RuntimeException e) {
					activity.runOnUiThread(new MakeToast(e));
				}
			}
		}).start();
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
