
public class EnvironmentControllerImpl implements EnvironmentController {

	private HVAC hvac;
	private int fanCoolOff = 0;
	public boolean heatOn = false;
	public boolean coolOn = false;
	public boolean fanOn = false;

	@Override
	public HVAC getHvac() {
		return hvac;
	}

	@Override
	public void setHvac(HVAC hvac) {
		this.hvac = hvac;
	}

	EnvironmentControllerImpl(HVAC hvac) {
		this.hvac = hvac;
	}
	
	void tick() {
		
		if (hvac.temp() <= TemperatureValues.TOO_COOL.value) {
			heat(true);
			fan(true);
		} else if (hvac.temp() > TemperatureValues.JUST_RIGHT.value - 2 && !coolOn) {
			heat(false);
			fan(false);
		}
		
		if (hvac.temp() >= TemperatureValues.TOO_HOT.value) {
			cool(true);
			fan(true);
		} else if (hvac.temp() < TemperatureValues.JUST_RIGHT.value + 2 && !heatOn) {
			cool(false);
			fan(false);
		}
		fanCoolOff--;
	}
	
	@Override
	public void heat(boolean on) {
		if (on != heatOn) {
			hvac.heat(on);
			if (!on)
				setFanCoolOff(5);
			heatOn = on;
		}
	}
	
	@Override
	public void cool(boolean on) {
		if (on != coolOn) {
			hvac.cool(on);
			if (!on)
				setFanCoolOff(3);
			coolOn = on;
		}
	}
	
	@Override
	public void fan(boolean on) {
		if (on != fanOn) {
			if (fanCoolOff <= 0 || !on) {
				hvac.fan(on);
				fanOn = on;
			}
		}
	}

	@Override
	public void setHighTemp(int temp) {

	}

	@Override
	public void setLowTemp(int lowTemp) {

	}

	@Override
	public int getHighTemp() {
		return 0;
	}

	@Override
	public int getLowTemp() {
		return 0;
	}

	private void setFanCoolOff(int ticks) {
		if (fanCoolOff < ticks)
			fanCoolOff = ticks;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
