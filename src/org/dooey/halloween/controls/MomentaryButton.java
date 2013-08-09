package org.dooey.halloween.controls;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MomentaryButton implements OnTouchListener {
	private static final int BACKGROUND_ON = 0xffff0000;
	private static final int BACKGROUND_OFF = 0xffaaaaaa;

	private final Server server;
	private final String name;
	private final Button button;
	
	public MomentaryButton(Server server, String name, Button button) {
		this.server = server;
		this.button = button;
		this.name = name;
		button.setOnTouchListener(this);
		button.setBackgroundColor(BACKGROUND_OFF);
	}
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			server.gpio(name, true);
			Log.d("ween", name + " is pushed");
			button.setBackgroundColor(BACKGROUND_ON );
		} else if (event.getAction() == MotionEvent.ACTION_UP){
			server.gpio(name, false);
			Log.d("ween", name + " is released");
			button.setBackgroundColor(BACKGROUND_OFF);
		}
		return true;
	}

}
