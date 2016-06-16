import java.io.IOException;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class CommandLine {
	public static EnvironmentControllerImpl controller;
	protected static SocketWrapper socketWrapper;
	protected static EnvironmentCommandManager manager;
	protected static HVAC hvac;

	public static void main(String[] args) throws IOException {
		int highTemp = Integer.valueOf(args[1]);
		int lowTemp = Integer.valueOf(args[1]);
		int port = Integer.valueOf(args[0]);

		hvac = getNewHVAC();
		controller = new EnvironmentControllerImpl(hvac);
		manager = new EnvironmentCommandManager(controller, highTemp, lowTemp);
		socketWrapper = new SocketWrapper(manager, port);
	}

	protected static HVAC getNewHVAC(){
		return new FinishedHVAC();
	}
}
