package com.supinfo.jforum.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BinaryConverterServer implements Runnable {

	private Socket socket;
	
	
	public BinaryConverterServer(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			String message = readSocketStream();
			String binaryString = convertToBinaryString(message);
			writeSocketStream(binaryString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	private void writeSocketStream(String binaryString) throws IOException {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.write(binaryString);
			printWriter.flush();
		} catch (IOException e) {
			throw new IOException("Impossible to send response to the client.", e);
		}
	}

	private String convertToBinaryString(String message) {
		StringBuilder sb = new StringBuilder();
		for (byte b : message.getBytes()) {
			if(b != 0) {
				sb.append(Integer.toBinaryString(b));
				sb.append(" ");
			} else {
				break;
			}
		}
		return sb.toString();
	}

	private String readSocketStream() throws IOException  {
		String message = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);
			char[] target = new char[128];
			bufferedReader.read(target);
			message = String.valueOf(target);
		} catch (IOException e) {
			throw new IOException("Impossible to retrieve message from the client.", e);
		}
		return message;
	}

}
