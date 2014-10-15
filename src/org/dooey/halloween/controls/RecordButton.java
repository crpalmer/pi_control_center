package org.dooey.halloween.controls;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class RecordButton implements OnTouchListener {
	private static final String TAG = "RecordButton";
	private static final int BACKGROUND_OFF = 0xff00ff00;
	private static final int BACKGROUND_ON = 0xffff0000;

	private final Button button;
	private MomentaryWork work;

	public RecordButton(Button button, MomentaryWork work) {
		this.work = work;
		this.button = button;
		button.setOnTouchListener(this);
		button.setBackgroundColor(BACKGROUND_OFF);
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			work.startWork();
			Log.d(TAG, "ween is pushed");
			button.setBackgroundColor(BACKGROUND_ON);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			Log.d(TAG, "ween is released");
			work.completeWork();
			button.setBackgroundColor(BACKGROUND_OFF);
		}
		return true;
	}
}
