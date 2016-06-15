import java.lang.IllegalArgumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class EnvironmentCommandManager {
	private final EnvironmentController controller;
	private int initialHighTemp = 75;
	private int initialLowTemp = 65;

	public EnvironmentCommandManager(EnvironmentController controller) {
		this.controller = controller;
	}

	public EnvironmentCommandManager(EnvironmentController controller, int initialHighTemp, int initialLowTemp) {
		this.controller = controller;
		this.initialHighTemp = initialHighTemp;
		this.initialLowTemp = initialLowTemp;
	}

	public void setTemp(String message) throws IllegalArgumentException {
		Map<String, Integer> parsedMessages = parseCommands(message);

		if(parsedMessages.containsKey("highTemp")) {
			setHighTemp(parsedMessages.get("highTemp"));
		}

		if(parsedMessages.containsKey("lowTemp")){
			setLowTemp(parsedMessages.get("lowTemp"));
		}
	}

	private Map<String, Integer> parseCommands(String message){
		Map<String, Integer> commands = new HashMap<String, Integer>();
		String[] multipleMessages = message.split(",");

		for(String msg : multipleMessages){
			String[] parsedMessage = msg.split(":");
			commands.put(parsedMessage[0],Integer.valueOf(parsedMessage[1]));
		}
		return commands;
	}

	private void setLowTemp(Integer lowTemp) {
		int highTemp = this.controller.getHighTemp();

		if(highTemp - lowTemp < 5){
			String exceptionMessage = "Low Temperature should be at least 5 degrees cooler than high. High temp set to "+highTemp+".";
			throw new IllegalArgumentException(exceptionMessage);
		}

		controller.setLowTemp(lowTemp);

	}

	private void setHighTemp(int highTemp) throws IllegalArgumentException {
		int lowTemp = this.controller.getLowTemp();

		if(highTemp - lowTemp < 5){
			String exceptionMessage = "High Temperature should be at least 5 degrees warmer than low. Low temp set to "+lowTemp+".";
			throw new IllegalArgumentException(exceptionMessage);
		}

		controller.setHighTemp(highTemp);
	}

	public int getInitialHighTemp() {
		return this.initialHighTemp;
	}

	public int getInitialLowTemp() {
		return this.initialLowTemp;
	}
}
