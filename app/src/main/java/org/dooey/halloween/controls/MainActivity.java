package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

public class MainActivity extends Activity {
	private RemoteButton ghost_dog, frog_log, question, snake, cat;
	private RemoteButton mr_hop;
	private RemoteButton less, more, burst;
	private RecordButton speak;
	private Remote animationStationRemote, scowRemote, foggerRemote,
			invadersRemote;

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

		animationStationRemote = new Remote("animation-station.dooey.org", 5555);
		scowRemote = new Remote("scow.dooey.org", 5555);
        foggerRemote = new Remote("fogger.dooey.org", 5556);
        invadersRemote = new Remote("invaders.dooey.org", 5555);

		final Button button1 = (Button) findViewById(R.id.button1);
		ghost_dog = new RemoteButton(button1, new RemotePublisher(this,
				button1, animationStationRemote, "chicken"));
		final Button button2 = (Button) findViewById(R.id.button2);
		frog_log = new RemoteButton(button2, new RemotePublisher(this,
				button2, animationStationRemote, "squid"));
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(questionButton, new RemotePublisher(this,
				questionButton, animationStationRemote, "question"));
		final Button button4 = (Button) findViewById(R.id.button4);
		snake = new RemoteButton(button4, new RemotePublisher(this,
				button4, animationStationRemote, "frog"));
		final Button button5 = (Button) findViewById(R.id.button5);
		cat = new RemoteButton(button5, new RemotePublisher(this,
				button5, animationStationRemote, "intestines"));

		final Button scowButton = (Button) findViewById(R.id.scow);
		mr_hop = new RemoteButton(scowButton, new RemotePublisher(this,
				scowButton, scowRemote, "scow"));

		final Button mm1Button = (Button) findViewById(R.id.mm1);
		mr_hop = new RemoteButton(mm1Button, new RemotePublisher(this,
				mm1Button, invadersRemote, "mean-mode 1"));

		final Button resetScoreButton = (Button) findViewById(R.id.high_score);
		mr_hop = new RemoteButton(resetScoreButton, new RemotePublisher(this,
				resetScoreButton, invadersRemote, "reset-high-score"));

		final Button mm2Button = (Button) findViewById(R.id.mm2);
		mr_hop = new RemoteButton(mm2Button, new RemotePublisher(this,
				mm2Button, invadersRemote, "mean-mode 2"));

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
				new RemotePublisher(this, speakButton, invadersRemote, "play")));
	}
}
