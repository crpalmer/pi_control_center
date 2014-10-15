package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

public class MainActivity extends Activity {
	private RemoteButton spider, zombie, question, gater, snake;
	private RemoteButton burp, spit;
	private RecordButton speak;
	private Remote animationStationRemote, spitterRemote, voodooRemote;

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

		animationStationRemote = new Remote("pi4", 5555);
		spitterRemote = new Remote("pi1", 5555);
		voodooRemote = new Remote("pi3", 5555);

		final Button spiderButton = (Button) findViewById(R.id.spider_button);
		spider = new RemoteButton(spiderButton, new RemotePublisher(this,
				animationStationRemote, "spider"));
		final Button zombieButton = (Button) findViewById(R.id.zombie_button);
		zombie = new RemoteButton(zombieButton, new RemotePublisher(this,
				animationStationRemote, "zombie"));
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(questionButton, new RemotePublisher(this,
				animationStationRemote, "question"));
		final Button gaterButton = (Button) findViewById(R.id.gater_button);
		gater = new RemoteButton(gaterButton, new RemotePublisher(this,
				animationStationRemote, "gater"));
		final Button snakeButton = (Button) findViewById(R.id.snake_button);
		snake = new RemoteButton(snakeButton, new RemotePublisher(this,
				animationStationRemote, "snake"));

		final Button spitButton = (Button) findViewById(R.id.spit_button);
		spit = new RemoteButton(spitButton, new RemotePublisher(this,
				spitterRemote, "spit"));
		final Button burpButton = (Button) findViewById(R.id.burp_button);
		burp = new RemoteButton(burpButton, new RemotePublisher(this,
				spitterRemote, "burp"));

		final Button speakButton = (Button) findViewById(R.id.speak_button);
		speak = new RecordButton(speakButton, new RecordingWork(
				new RemotePublisher(this, voodooRemote, "play")));
	}
}
