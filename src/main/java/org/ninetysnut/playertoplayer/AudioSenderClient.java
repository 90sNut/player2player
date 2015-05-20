package org.ninetysnut.playertoplayer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

class AudioSenderClient {

	private static final Logger log = Logger.getAnonymousLogger();
	private static final int OUTPUT_BUFFER_SIZE = 1024; // em bytes. Valor
														// antigo: 128000
	private static final File SOUND_FILE = new File("c:\\tmp\\audio.wav");
	private static AudioInputStream audioInputStream = null;
	private static String hostRemoto = "177.148.233.229";
	private static int port = 64091;

	public static void main(String args[]) throws IOException {

		InetAddress ipRemoto = InetAddress.getByName(hostRemoto);

		log.info("Tentando conectar Socket...");
		Socket clientSocket = new Socket(ipRemoto, port, null, 0);
		log.info("Conectado!");

		log.info("Iniciando tranferencia de " + SOUND_FILE);
		DataOutputStream os = new DataOutputStream(
				clientSocket.getOutputStream());

		try {
			audioInputStream = AudioSystem.getAudioInputStream(SOUND_FILE);
		} catch (Exception e) {
			log.severe(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		int nBytesRead = 0;
		long nBytesWritten = 0;

		byte[] abData = new byte[OUTPUT_BUFFER_SIZE];

		while (nBytesRead != -1) {
			log.info("Bytes transferidos: " + nBytesWritten);
			nBytesRead = audioInputStream.read(abData, 0, abData.length);
			if (nBytesRead >= 0) {
				System.out.println(abData);
				os.write(abData, 0, nBytesRead);
				os.flush();
				nBytesWritten += nBytesRead;
			}
		}
	}
}