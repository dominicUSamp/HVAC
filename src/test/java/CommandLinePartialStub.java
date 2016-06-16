/**
 * Created by dominicscimeca on 6/16/16.
 */
public class CommandLinePartialStub extends CommandLine {
	private static HVAC mockHVAC;

	public static void setHVAC(HVAC hvac){
		mockHVAC = hvac;
	}

	public static EnvironmentController getController(){
		return controller;
	}

	protected static HVAC getNewHVAC(){
		return mockHVAC;
	}
}
