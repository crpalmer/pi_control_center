package org.dooey.halloween.controls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	public final Socket socket;
	public final PrintWriter out;
	public final BufferedReader in;

	public Server(String host, int port) throws UnknownHostException,
			IOException {
		try {
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (UnknownHostException e) {
			throw new RuntimeException("Couldn't connect to [" + "raspberrypi1"
					+ "]", e);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't connect to [" + "raspberrypi1"
					+ "]", e);
		}
	}

	public void gpio(String name, boolean isOn) {
		out.println((isOn ? "ON" : "OFF") + " " + name);
	}
}
