/**
 * Created by dominicscimeca on 6/15/16.
 */
public class EnvironmentControllerMock implements  EnvironmentController{

	public boolean highTempCalled = false;
	public int highTempCalledWith;
	public int lowTempCalledWith;
	public int lowTemp = 65;
	public int highTemp = 75;

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

	@Override
	public int getHighTemp() {
		return highTemp;
	}

	@Override
	public int getLowTemp() {
		return lowTemp;
	}
}
