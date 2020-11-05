package server.telran.net.server;

import java.io.IOException;
import java.net.*;

public class Server implements Runnable {
	ServerSocket serverSocket;
	int port;
	Protocol protocol;

	public Server(int port, Protocol protocol) {
		this.port = port;
		this.protocol = protocol;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException("Port in use " + port);
		}
	}

	@Override
	public void run() {
		System.out.println("Server is listening on port " + port);
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				ServerClient client = new ServerClient(socket, protocol);
				client.run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
