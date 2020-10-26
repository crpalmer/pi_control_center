package org.dooey.halloween.controls;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemoteButton implements OnClickListener {
	private final EventPublisher publisher;

	public RemoteButton(Button button, EventPublisher publisher) {
		this.publisher = publisher;
		button.setOnClickListener(this);
	}

	public void onClick(View unused) {
		publisher.publish();
	}
}
