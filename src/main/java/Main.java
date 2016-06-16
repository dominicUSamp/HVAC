import java.io.IOException;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class Main {
	public static void main(String[] args) throws IOException {
		int highTemp = 100;
		int lowTemp = 50;
		int port = 1919;

		HVAC hvac = new HVAC() {
			@Override
			public void heat(boolean on) {

			}

			@Override
			public void cool(boolean on) {

			}

			@Override
			public void fan(boolean on) {

			}

			@Override
			public int temp() {
				return 0;
			}
		};

		EnvironmentController controller = new EnvironmentControllerImpl(hvac);
		EnvironmentCommandManager manager = new EnvironmentCommandManager(controller, highTemp, lowTemp);
		SocketWrapper socketWrapper = new SocketWrapper(manager, port);
	}
}
