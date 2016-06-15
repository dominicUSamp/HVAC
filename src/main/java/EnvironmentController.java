/**
 * Created by dominicscimeca on 6/15/16.
 */
public interface EnvironmentController {
	HVAC getHvac();

	void setHvac(HVAC hvac);

	void heat(boolean on);

	void cool(boolean on);

	void fan(boolean on);

	void setHighTemp(int temp);

	void setLowTemp(int lowTemp);

	int getHighTemp();

	int getLowTemp();

	public enum TemperatureValues {
		TOO_COOL(64), JUST_RIGHT(70), TOO_HOT(76);

		public int value;
		TemperatureValues(int value) {
			this.value = value;
		}
	}
}
