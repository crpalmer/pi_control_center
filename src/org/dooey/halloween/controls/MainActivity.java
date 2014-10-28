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
	private Remote animationStationRemote, spitterRemote, swampRemote,
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

		animationStationRemote = new Remote("pi4", 5555);
		spitterRemote = new Remote("pi1", 5555);
		swampRemote = new Remote("192.168.1.169", 5555);
		voodooRemote = new Remote("pi3", 5555);

		final Button spiderButton = (Button) findViewById(R.id.spider_button);
		spider = new RemoteButton(spiderButton, new RemotePublisher(this,
				spiderButton, animationStationRemote, "spider"));
		final Button zombieButton = (Button) findViewById(R.id.zombie_button);
		zombie = new RemoteButton(zombieButton, new RemotePublisher(this,
				zombieButton, animationStationRemote, "zombie"));
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(questionButton, new RemotePublisher(this,
				questionButton, animationStationRemote, "question"));
		final Button gaterButton = (Button) findViewById(R.id.gater_button);
		gater = new RemoteButton(gaterButton, new RemotePublisher(this,
				gaterButton, animationStationRemote, "gater"));
		final Button snakeButton = (Button) findViewById(R.id.snake_button);
		snake = new RemoteButton(snakeButton, new RemotePublisher(this,
				snakeButton, animationStationRemote, "snake"));

		final Button spitButton = (Button) findViewById(R.id.spit_button);
		spit = new RemoteButton(spitButton, new RemotePublisher(this,
				spitButton, spitterRemote, "spit"));
		final Button burpButton = (Button) findViewById(R.id.burp_button);
		burp = new RemoteButton(burpButton, new RemotePublisher(this,
				burpButton, spitterRemote, "burp"));

		final Button lessButton = (Button) findViewById(R.id.less_button);
		less = new RemoteButton(lessButton, new RemotePublisher(this,
				lessButton, swampRemote, "duty_down"));
		final Button fogButton = (Button) findViewById(R.id.fog_button);
		burst = new RemoteButton(fogButton, new RemotePublisher(this,
				fogButton, swampRemote, "fog"));
		final Button moreButton = (Button) findViewById(R.id.more_button);
		more = new RemoteButton(moreButton, new RemotePublisher(this,
				moreButton, swampRemote, "duty_up"));

		final Button speakButton = (Button) findViewById(R.id.speak_button);
		speak = new RecordButton(speakButton, new RecordingWork(
				new RemotePublisher(this, speakButton, voodooRemote, "play")));
	}
}
