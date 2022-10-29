package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

public class MainActivity extends Activity {
	public MainActivity() {
		super();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	private RemoteButton createPropAction(Remote remote, int buttonId, String command) {
		final Button button = findViewById(buttonId);
		return new RemoteButton(button, new RemotePublisher(this,
				button, remote, command));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Remote animationStationRemote = new Remote("animation-station.dooey.org", 5555);
		Remote bandRemote = new Remote("band.dooey.org", 5555);
		Remote foggerRemote = new Remote("talker.dooey.org", 5556);
		Remote talkerRemote = new Remote("talker.dooey.org", 5555);

		createPropAction(animationStationRemote, R.id.button1, "snake");
		createPropAction(animationStationRemote, R.id.button2, "bunny");
		createPropAction(animationStationRemote, R.id.button3, "M&M");
		createPropAction(animationStationRemote, R.id.button4, "demono");
		createPropAction(foggerRemote, R.id.fog_button, "fog");
		createPropAction(foggerRemote, R.id.more_button, "duty_up");
		createPropAction(foggerRemote, R.id.less_button, "duty_down");
		createPropAction(bandRemote, R.id.band, "play");

		final Button speakButton = findViewById(R.id.speak_button);
		new RecordButton(speakButton, new RecordingWork(
				new RemotePublisher(this, speakButton, talkerRemote, "play")));
	}
}
