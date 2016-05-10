package org.ninetysnut.playertoplayer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder extends Thread {

	private static final Logger log = Logger.getAnonymousLogger();
	private static String hostRemoto = "179.34.205.253"; //?
	
	private static int port = 59430;

	private TargetDataLine targetDataLine;
	private AudioFileFormat.Type audioFileFormatType;
	private AudioInputStream audioInputStream;
	private File outputFile;

	public AudioRecorder(TargetDataLine targetDataLine,
			AudioFileFormat.Type audioFileFormatType, File outputFile) {
		this.targetDataLine = targetDataLine;
		this.audioInputStream = new AudioInputStream(targetDataLine);
		this.audioFileFormatType = audioFileFormatType;
		this.outputFile = outputFile;
	}

	public static AudioRecorder build(AudioFormat audioFormat,
			AudioFileFormat.Type audioFileFormatType, File outputFile) {

		DataLine.Info info = new DataLine.Info(TargetDataLine.class,
				audioFormat);

		TargetDataLine targetDataLine = null;
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			log.info("Não foi possível pegar a TargetDataLine.");
			e.printStackTrace();
			System.exit(1);
		}

		AudioRecorder recorder = new AudioRecorder(targetDataLine,
				audioFileFormatType, outputFile);

		return recorder;
	}

	/**
	 * Starts the line then starts the thread
	 */
	public void start() {
		targetDataLine.start();
		super.start();
	}

	/**
	 * Stops the line then close it.
	 */
	public void stopRecording() {
		targetDataLine.stop();
		targetDataLine.close();
	}

	/**
	 * The data is read from TargetDataLine through the AudioInputStream then
	 * written to the passed File. Before writing of audio data starts, a header
	 * is written according to the desired audio file type. Reading continues
	 * until TargetDataLine is stopped or closed. Then, the file is closed and
	 * 'AudioSystem.write()' returns.
	 */
	public void run() {
		try {
			InetAddress ipRemoto = InetAddress.getByName(hostRemoto);

			log.info("Tentando conectar Socket...");
			Socket clientSocket = new Socket(ipRemoto, port);

			log.info("Iniciando tranferencia de InputAundio");
			OutputStream outputStream = clientSocket.getOutputStream();
			AudioSystem
					.write(audioInputStream, audioFileFormatType, outputStream);
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
}