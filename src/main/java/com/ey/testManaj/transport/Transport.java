package com.ey.testManaj.transport;



import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * The type Transport.
 */
public class Transport {
	
	private static NetHttpTransport instance;
	
	private Transport() {
		
	}

	/**
	 * Gets http transport instance.
	 *
	 * @return The http transport instance
	 */
	public static NetHttpTransport getInstance() {
		if(instance == null) {
			synchronized (Transport.class) {
				if(instance == null) {
					instance = new NetHttpTransport();
				}
			}
		}
		return instance;
	}
	
}
