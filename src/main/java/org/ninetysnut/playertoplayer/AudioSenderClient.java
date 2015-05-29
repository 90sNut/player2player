package org.ninetysnut.playertoplayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
//	private static String hostRemoto = "177.148.233.229"; //batrakius
//	private static String hostRemoto = "179.153.81.244"; //mustache
//	private static String hostRemoto = "b39951f4.virtua.com.br"; //mustache
//	private static String hostRemoto = "186.203.15.27"; //?
	private static String hostRemoto = "179.34.58.234"; //?
	
	
//	private static int port = 17964;
//	private static String hostRemoto = "192.168.1.111"; //mustache lan
	private static int port = 6666;
	
	public static void main(String args[]) throws IOException {

		InetAddress ipRemoto = InetAddress.getByName(hostRemoto);

		log.info("Tentando conectar Socket...");
		Socket clientSocket = new Socket(ipRemoto, port, null, 0);
		log.info("Conectado!");

		log.info("Iniciando tranferencia de " + SOUND_FILE);
		OutputStream outputStream = clientSocket.getOutputStream();

		InputStream inputStream = new BufferedInputStream(new FileInputStream(SOUND_FILE));
		
		int nBytesRead = 0;
		byte[] abData = new byte[OUTPUT_BUFFER_SIZE];
		int nBytesWritten = 0;
		
		log.info("Transfering...");
		while (nBytesRead != -1) {
			try {
				log.info("Transferidos bytes: " + nBytesWritten);
				nBytesRead = inputStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				outputStream.write(abData, 0, nBytesRead);
				outputStream.flush();
//				System.out.println(abData);
				nBytesWritten += nBytesRead;
			}
		}
		log.info("Transfering finished");
	}
}