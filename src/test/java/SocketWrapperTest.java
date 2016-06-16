import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class SocketWrapperTest {

	@Test
	public void testMessageSentToManager() throws IOException, InterruptedException {
		String data = "first message";
		final int port = 1919;

		CountDownLatch tempCalled = new CountDownLatch(1);

		final EnvironmentCommandManagerStub stub = new EnvironmentCommandManagerStub(tempCalled);

		new Thread() {public void run(){
				try {
					SocketWrapper wrapper = new SocketWrapper(stub, port);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}}.start();


		Socket socket = new Socket("localhost",port);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		out.println(data);
		out.flush();

		tempCalled.await();

		assertTrue(stub.tempCalled);
		assertEquals(data, stub.setTempCalledWith);
	}
}
