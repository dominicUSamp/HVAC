
public class EnvironmentController {

	private HVAC hvac;
	private int fanCoolOff = 0;

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
		
		if (hvac.temp() < 65) {
			heat(true);
			fan(true);
		}
		
		if (hvac.temp() > 75) {
			cool(true);
			fan(true);
		}
		fanCoolOff--;
	}
	
	public void heat(boolean on) {
		hvac.heat(on);
		if (on == false) 
			setFanCoolOff(5);
	}
	
	public void cool(boolean on) {
		hvac.cool(on);
		if (on == false) 
			setFanCoolOff(3);
	}
	
	public void fan(boolean on) {
		if (fanCoolOff == 0 || on == false)
			hvac.fan(on);
	}
	
	private void setFanCoolOff(int ticks) {
		if (fanCoolOff < ticks)
			fanCoolOff = ticks;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
