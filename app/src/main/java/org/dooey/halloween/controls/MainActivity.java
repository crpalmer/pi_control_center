package org.dooey.halloween.controls;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends Activity {
	private RemoteButton spider, zombie, question, gater, snake;
	private RemoteButton burp, spit;
	private RemoteButton less, more, burst;
	private RecordButton speak;
	private Remote animationStationRemote, eelRemote, foggerRemote,
			talkerRemote;

    private static final String BUTTON1 = "snake";
    private static final String BUTTON2 = "frog";
    private static final String BUTTON4 = "gater";
    private static final String BUTTON5 = "spider";

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

        animationStationRemote = new Remote("animation-station", 5555);
        foggerRemote = new Remote("animation-station", 5556);
        talkerRemote = new Remote("talker", 5555);

		final Button button1 = (Button) findViewById(R.id.button1);
		spider = new RemoteButton(button1, new RemotePublisher(this,
				button1, animationStationRemote, BUTTON1));
		final Button button2 = (Button) findViewById(R.id.button2);
		zombie = new RemoteButton(button2, new RemotePublisher(this,
				button2, animationStationRemote, BUTTON2));
		final Button questionButton = (Button) findViewById(R.id.question_button);
		question = new RemoteButton(questionButton, new RemotePublisher(this,
				questionButton, animationStationRemote, "question"));
		final Button button4 = (Button) findViewById(R.id.button4);
		gater = new RemoteButton(button4, new RemotePublisher(this,
				button4, animationStationRemote, BUTTON4));
		final Button button5 = (Button) findViewById(R.id.button5);
		snake = new RemoteButton(button5, new RemotePublisher(this,
				button5, animationStationRemote, BUTTON5));

        final Button mermaidButton = (Button) findViewById(R.id.mermaid);
        snake = new RemoteButton(mermaidButton, new RemotePublisher(this,
                button5, animationStationRemote, "mermaid"));

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
				new RemotePublisher(this, speakButton, talkerRemote, "play")));
	}
}
