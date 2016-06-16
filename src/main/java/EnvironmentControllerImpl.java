
public class EnvironmentControllerImpl implements EnvironmentController {

	private HVAC hvac;
	private int fanCoolOff = 0;
	public boolean heatOn = false;
	public boolean coolOn = false;
	public boolean fanOn = false;

	private int highTemp = initialHighTemp;
	private int lowTemp = initialLowTemp;

	EnvironmentControllerImpl(HVAC hvac) {
		this.hvac = hvac;
	}
	
	void tick() {
		int temp = hvac.temp();

		heat((temp < this.lowTemp));
		cool((temp > this.highTemp));
		fan((temp > this.highTemp || temp < this.lowTemp));

		fanCoolOff--;
	}

	private void heat(boolean on) {
		if (on != heatOn) {
			hvac.heat(on);
			if (!on)
				setFanCoolOff(5);
			heatOn = on;
		}
	}

	private void cool(boolean on) {
		if (on != coolOn) {
			hvac.cool(on);
			if (!on)
				setFanCoolOff(3);
			coolOn = on;
		}
	}

	private void fan(boolean on) {
		if (on != fanOn) {
			if (fanCoolOff <= 0 || !on) {
				hvac.fan(on);
				fanOn = on;
			}
		}
	}

	private void setFanCoolOff(int ticks) {
		if (fanCoolOff < ticks)
			fanCoolOff = ticks;
	}

	@Override
	public void setHighTemp(int temp) {
		this.highTemp = temp;
	}

	@Override
	public void setLowTemp(int temp) {
		this.lowTemp = temp;
	}

	@Override
	public int getHighTemp() {
		return this.highTemp;
	}

	@Override
	public int getLowTemp() {
		return this.lowTemp;
	}
}
