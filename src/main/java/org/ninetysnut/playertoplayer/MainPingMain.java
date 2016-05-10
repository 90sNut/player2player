package org.ninetysnut.playertoplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainPingMain {
	public static void main(String[] args) {
		System.out.println("Ping Poller Starts...");

		// mustache1up = "179.153.81.179";
		// batrakius = 186.203.207.193

		try {
			InetAddress inet = InetAddress.getByAddress(new byte[] {
					(byte) 186, (byte) 203, (byte) 207, (byte) 193 });
			System.out.println("Sending Ping Request to " + "ZYX");

			boolean status = inet.isReachable(5000); // Timeout = 5000 milli
														// seconds

			if (status) {
				System.out.println("Status : Host is reachable");
			} else {
				System.out.println("Status : Host is not reachable");
			}
		} catch (UnknownHostException e) {
			System.err.println("Host does not exists");
		} catch (IOException e) {
			System.err.println("Error in reaching the Host");
		}
	}
}