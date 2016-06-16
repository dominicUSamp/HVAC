import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by dominicscimeca on 6/16/16.
 */
public class CommandLineTest {
	@Test
	public void testMain() throws IOException, InterruptedException {
		int highTemp = 100;
		int lowTemp = 50;
		final int port = 1919;

		final String[] args = {String.valueOf(port),String.valueOf(lowTemp),String.valueOf(highTemp)};
		HVAC mock = new HVACMock(70, false, false, false);

		CommandLinePartialStub.setHVAC(mock);

		new Thread() {public void run(){
			try {
				CommandLinePartialStub.main(args);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}}.start();

		int newHighTemp = 70;

		String data = "highTemp:"+newHighTemp;
		Socket socket = new Socket("localhost",port);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		out.println(data);
		out.flush();

		Thread.sleep(1000);

		assertEquals(newHighTemp, CommandLinePartialStub.getController().getHighTemp());
		assertEquals(lowTemp, CommandLinePartialStub.getController().getLowTemp());
	}
}
