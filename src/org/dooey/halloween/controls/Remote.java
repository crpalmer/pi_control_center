package org.dooey.halloween.controls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Remote {
	private static final String TAG = "remote";
	private final String host;
	private final int port;

	public Remote(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String command(String cmd) {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			Log.d(TAG, "connect to " + host + ":" + port);
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			Log.d(TAG, "send to [" + host + ":" + port + "]: " + cmd);
			out.println(cmd);
			String response = in.readLine();
			Log.d(TAG, "response: " + response);
			if (!"ok".equals(response)) {
				String message = "Command \"" + cmd + "\" failed: " + response;
				Log.d(TAG, message);
				throw new RuntimeException(message);
			}
			return response;
		} catch (UnknownHostException e) {
			String message = "Couldn't connect to [" + host + ":" + port
					+ "]: " + e.getLocalizedMessage();
			Log.d(TAG, message);
			throw new RuntimeException(message, e);
		} catch (IOException e) {
			String message = "Couldn't connect to [" + host + ":" + port
					+ "]: " + e.getLocalizedMessage();
			Log.d(TAG, message);
			throw new RuntimeException(message, e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				out.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
