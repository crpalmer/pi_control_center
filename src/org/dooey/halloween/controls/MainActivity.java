package org.dooey.halloween.controls;

import java.io.IOException;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends Activity {
	private MomentaryButton spider;
	private final Server server;

	public MainActivity() {
		super();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			server = new Server("192.168.1.153", 5555);
		} catch (UnknownHostException e) {
			throw new RuntimeException("Couldn't connect to [" + "raspberrypi1"
					+ "]", e);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't connect to [" + "raspberrypi1"
					+ "]", e);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button spiderButton = (Button) findViewById(R.id.spider_button);
		spider = new MomentaryButton(server, "spider", spiderButton);
		final Button zombieButton = (Button) findViewById(R.id.zombie_button);
		spider = new MomentaryButton(server, "zombie", zombieButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
