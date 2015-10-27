package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

public class MainActivity extends Activity {
	private RemoteButton spider, zombie, question, gater, snake;
	private RemoteButton burp, spit;
	private RemoteButton less, more, burst;
	private RecordButton speak;
	private Remote animationStationRemote, eelRemote, foggerRemote,
			voodooRemote;

	public MainActivity() {
		super();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		animationStationRemote = new Remote("pi3", 5555);
		eelRemote = new Remote("pi3", 5555);
		foggerRemote = new Remote("192.168.1.169", 5555);
		voodooRemote = new Remote("pi3", 5555);

		final Button octoButton = (Button) findViewById(R.id.octo_button);
		spider = new RemoteButton(octoButton, new RemotePublisher(this,
				octoButton, animationStationRemote, "octo"));
		final Button squidButton = (Button) findViewById(R.id.squid_button);
		zombie = new RemoteButton(squidButton, new RemotePublisher(this,
				squidButton, animationStationRemote, "squid"));
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(questionButton, new RemotePublisher(this,
				questionButton, animationStationRemote, "question"));
		final Button unknownButton = (Button) findViewById(R.id.unknown_button);
		gater = new RemoteButton(unknownButton, new RemotePublisher(this,
				unknownButton, animationStationRemote, "???"));
		final Button cudaButton = (Button) findViewById(R.id.cuda_button);
		snake = new RemoteButton(cudaButton, new RemotePublisher(this,
				cudaButton, animationStationRemote, "cuda"));

		final Button eelButton = (Button) findViewById(R.id.eel_button);
		spit = new RemoteButton(eelButton, new RemotePublisher(this,
				eelButton, eelRemote, "eel"));

		final Button lessButton = (Button) findViewById(R.id.less_button);
		less = new RemoteButton(lessButton, new RemotePublisher(this,
				lessButton, foggerRemote, "duty_down"));
		final Button fogButton = (Button) findViewById(R.id.fog_button);
		burst = new RemoteButton(fogButton, new RemotePublisher(this,
				fogButton, foggerRemote, "fog"));
		final Button moreButton = (Button) findViewById(R.id.more_button);
		more = new RemoteButton(moreButton, new RemotePublisher(this,
				moreButton, foggerRemote, "duty_up"));

		final Button speakButton = (Button) findViewById(R.id.speak_button);
		speak = new RecordButton(speakButton, new RecordingWork(
				new RemotePublisher(this, speakButton, voodooRemote, "play")));
	}
}
