
public class EnvironmentController {
	
	public enum TemperatureValues {
		TOO_COOL(64), JUST_RIGHT(70), TOO_HOT(76);
		
		public int value;
		TemperatureValues(int value) {
			this.value = value;
		}
	}

	private HVAC hvac;
	private int fanCoolOff = 0;
	public boolean heatOn = false;
	public boolean coolOn = false;
	public boolean fanOn = false;

	public HVAC getHvac() {
		return hvac;
	}

	public void setHvac(HVAC hvac) {
		this.hvac = hvac;
	}

	EnvironmentController(HVAC hvac) {
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
	
	public void heat(boolean on) {
		if (on != heatOn) {
			hvac.heat(on);
			if (on == false) 
				setFanCoolOff(5);
			heatOn = on;
		}
	}
	
	public void cool(boolean on) {
		if (on != coolOn) {
			hvac.cool(on);
			if (on == false) 
				setFanCoolOff(3);
			coolOn = on;
		}
	}
	
	public void fan(boolean on) {
		if (on != fanOn) {
			if (fanCoolOff <= 0 || on == false) {
				hvac.fan(on);
				fanOn = on;
			}
		}
	}
	
	private void setFanCoolOff(int ticks) {
		if (fanCoolOff < ticks)
			fanCoolOff = ticks;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
