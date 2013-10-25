package com.supinfo.jforum.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientLauncher {
	
	public static void main(String[] args) {
		System.out.println("Please type a sentence to translate: ");
		Scanner scanner = new Scanner(System.in);
		String sentence = scanner.nextLine();
		scanner.close();

		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		
		try {
			
			Socket socket = new Socket("127.0.0.1", 4637);
			printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.print(sentence);
			printWriter.flush();
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(bufferedReader.readLine());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			if(printWriter != null) {
				printWriter.close();
			}
			
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
