package org.ninetysnut.playertoplayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioReceiverServer {

	static int EXTERNAL_BUFFER_SIZE = 128000;
	
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		
		ServerSocket server = new ServerSocket(0);
		
		System.out.println(server.getInetAddress().getHostAddress());
		System.out.println(server.getInetAddress().getHostName());
		
		System.out.println(server.getLocalPort());
		System.out.println(server.getLocalSocketAddress());
		
		Socket accept = server.accept();
		
		System.out.println(accept.getInetAddress().getHostAddress());
		System.out.println(accept.getPort());
		
		InputStream inputStream = accept.getInputStream();
		int bufferSize = 2;

		AudioPlayer player = AudioPlayer.build(inputStream, bufferSize);

		player.startPlayback();
	}
}
