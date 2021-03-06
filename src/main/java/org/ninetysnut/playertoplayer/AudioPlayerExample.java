package org.ninetysnut.playertoplayer;

import java.io.File;
import java.io.IOException;

public class AudioPlayerExample extends Thread {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		File fileToPlay = new File("c:\\tmp\\audio.wav");
		int bufferSize = 2;
		// Min buffer worked for mustache1up hardware: 2

		AudioPlayer player = AudioPlayer.build(fileToPlay, bufferSize);

		player.startPlayback();
	}
}
