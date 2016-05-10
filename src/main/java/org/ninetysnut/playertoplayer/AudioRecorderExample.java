package org.ninetysnut.playertoplayer;

import java.io.File;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class AudioRecorderExample {

	private static final Logger log = Logger.getAnonymousLogger();

	public static void main(String[] args) throws InterruptedException {

		AudioFormat audioFormat = Utils.getMonoAudioFormat();
		AudioFileFormat.Type audioFileFormatType = AudioFileFormat.Type.AU;
		File outputFile = new File("c:\\tmp\\audio222.wav");

		log.info("Trying to set things up to start recording audio...");
		AudioRecorder recorder = AudioRecorder.build(audioFormat,
				audioFileFormatType, outputFile);

		recorder.start();
		log.info("Recording...");

		Thread.sleep(30000);

		recorder.stopRecording();
		log.info("Recording stopped.");
	}
}
