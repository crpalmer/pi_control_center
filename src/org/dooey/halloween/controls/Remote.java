package org.dooey.halloween.controls;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

	public String command(String cmd, byte[] blob) {
		Socket socket = null;
		BufferedOutputStream out = null;
		BufferedReader in = null;
		try {
			Log.d(TAG, "connect to " + host + ":" + port);
			socket = new Socket(host, port);
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			Log.d(TAG, "send to [" + host + ":" + port + "]: " + cmd);
			out.write(cmd.getBytes());
			if (blob != null) {
				out.write(' ');
				printBlob(blob, out);
			}
			out.write('\n');
			out.flush();
			String response = in.readLine();
			Log.d(TAG, "response: " + response);

			if (response != null && response.startsWith("ok ")) {
				return response.substring(3);
			}

			if (!"ok".equals(response)) {
				String message = "Command \"" + cmd + "\" failed: " + response;
				Log.d(TAG, message);
				throw new RuntimeException(message);
			}

			return null;
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
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
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

	public String command(String cmd) {
		return command(cmd, null);
	}

	private void writeEscaped(char data, OutputStream os) throws IOException {
		os.write('\\');
		os.write(data);
	}

	private void printBlob(byte[] data, OutputStream os) throws IOException {

		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0)
				writeEscaped('0', os);
			else if (data[i] == '\\')
				writeEscaped('\\', os);
			else if (data[i] == '\n')
				writeEscaped('n', os);
			else if (data[i] == '\r')
				writeEscaped('r', os);
			else
				os.write(data[i]);
		}
	}
}
