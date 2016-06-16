import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class SocketWrapper {

	private final EnvironmentCommandManager manager;
	private final ServerSocket serverSocket;
	private final Socket socket;

	public SocketWrapper(EnvironmentCommandManager manager, int port) throws IOException {
		this.manager = manager;

		this.serverSocket = new ServerSocket(port);
		this.socket = this.serverSocket.accept();

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		String data = in.readLine();
		this.manager.setTemp(data);
	}
}
