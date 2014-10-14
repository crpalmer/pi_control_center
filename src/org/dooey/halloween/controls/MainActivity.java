package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity {
	private RemoteButton spider, zombie, question, gater, snake;
	private RemoteButton burp, spit;
	private Remote animationStationRemote, spitterRemote;

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

		final Button spiderButton = (Button) findViewById(R.id.spider_button);
		spider = new RemoteButton(this, spiderButton, animationStationRemote,
				"spider");
		final Button zombieButton = (Button) findViewById(R.id.zombie_button);
		zombie = new RemoteButton(this, zombieButton, animationStationRemote,
				"zombie");
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(this, questionButton,
				animationStationRemote, "question");
		final Button gaterButton = (Button) findViewById(R.id.gater_button);
		gater = new RemoteButton(this, gaterButton, animationStationRemote,
				"gater");
		final Button snakeButton = (Button) findViewById(R.id.snake_button);
		snake = new RemoteButton(this, snakeButton, animationStationRemote,
				"snake");

		final Button spitButton = (Button) findViewById(R.id.spit_button);
		spit = new RemoteButton(this, spitButton, spitterRemote, "spit");
		final Button burpButton = (Button) findViewById(R.id.burp_button);
		burp = new RemoteButton(this, burpButton, spitterRemote, "burp");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
