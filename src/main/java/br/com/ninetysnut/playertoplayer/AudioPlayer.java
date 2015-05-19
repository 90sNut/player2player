package br.com.ninetysnut.playertoplayer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {

	private static final Logger log = Logger.getAnonymousLogger();

	private final SourceDataLine sourceDataLine;
	private final AudioInputStream audioInputStream;
	private final int bufferSize;

	public AudioPlayer(SourceDataLine sourceDataLine,
			AudioInputStream audioInputStream, int bufferSize) {
		this.sourceDataLine = sourceDataLine;
		this.audioInputStream = audioInputStream;
		this.bufferSize = bufferSize;
	}

	public static AudioPlayer build(File fileToPlay, int bufferSize) {

		AudioInputStream audioInputStream = null;

		try {
			audioInputStream = AudioSystem.getAudioInputStream(fileToPlay);
		} catch (Exception e) {
			log.info("Não foi possível pegar o AudioInputStream do arquivo " + fileToPlay.getPath());
			e.printStackTrace();
			System.exit(1);
		}

		AudioFormat audioFormat = audioInputStream.getFormat();

		SourceDataLine sourceDataLine = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		}

		AudioPlayer player = new AudioPlayer(sourceDataLine, audioInputStream,
				bufferSize);

		return player;
	}

	/**
	 * Starts the line. Then, the data is read from TargetDataLine through the AudioInputStream then
	 * written to the passed File. Before writing of audio data starts, a header
	 * is written according to the desired audio file type. Reading continues
	 * until TargetDataLine is stopped or closed. Then, the file is closed and
	 * 'AudioSystem.write()' returns.
	 */
	public void startPlayback() {
		
		sourceDataLine.start();
		log.info("SourceDataLine started");

		int nBytesRead = 0;
		byte[] abData = new byte[bufferSize];
		
		log.info("Playing...");
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				int nBytesWritten = sourceDataLine.write(abData, 0, nBytesRead);
			}
		}
		log.info("Playing finished");
	}
}
