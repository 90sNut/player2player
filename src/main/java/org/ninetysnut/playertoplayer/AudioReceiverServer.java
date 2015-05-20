package org.ninetysnut.playertoplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioReceiverServer {

	static int EXTERNAL_BUFFER_SIZE = 128000;
	
	public static void main(String[] args) throws LineUnavailableException, IOException {
		
		
		ServerSocket server = new ServerSocket(64091);
		
		System.out.println(server.getInetAddress().getHostAddress());
		System.out.println(server.getInetAddress().getHostName());
		
		System.out.println(server.getLocalPort());
		
		Socket accept = server.accept();
		
		
		System.out.println(accept.getInetAddress().getHostAddress());
		System.out.println(accept.getPort());
		
		
		AudioFormat format = getAudioFormat();
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		
		AudioInputStream inputStream = (AudioInputStream) accept.getInputStream();

		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		int nBytesRead = inputStream.read(abData, 0, abData.length);
		while (nBytesRead != -1){
			line.write(abData, 0, nBytesRead);
			nBytesRead = inputStream.read(abData, 0, abData.length);
		}

	}

	public static AudioFormat getAudioFormat() {
		AudioFormat audioFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 1, 2, 44100.0F,
				false);
		return audioFormat;
	}

}
