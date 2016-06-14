
public class EnvironmentController {

	private HVAC hvac;
	private boolean heatTurnedOff;

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
	}
	
	public void heat(boolean on) {
		hvac.heat(on);
		if (on == false) 
			heatTurnedOff = true;
	}
	
	public void cool(boolean on) {
		hvac.cool(on);
	}
	
	public void fan(boolean on) {
		if (!heatTurnedOff)
			hvac.fan(on);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
