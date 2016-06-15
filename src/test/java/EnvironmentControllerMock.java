/**
 * Created by dominicscimeca on 6/15/16.
 */
public class EnvironmentControllerMock implements  EnvironmentController{

	public boolean highTempCalled = false;
	public int highTempCalledWith;
	public int lowTempCalledWith;

	@Override
	public HVAC getHvac() {
		return null;
	}

	@Override
	public void setHvac(HVAC hvac) {

	}

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
	public void setHighTemp(int temp) {
		highTempCalled = true;
		highTempCalledWith = temp;
	}

	@Override
	public void setLowTemp(int lowTemp) {
		lowTempCalledWith = lowTemp;
	}
}
