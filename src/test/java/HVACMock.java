
public class HVACMock implements HVAC {
	public boolean heatOn = false;
	public boolean coolOn = false;
	public boolean fanOn = false;
	private int temp;
	
	public HVACMock(int temp, boolean heatOn, boolean coolOn, boolean fanOn) {
		this.temp = temp;
		this.heatOn = heatOn;
		this.coolOn = coolOn;
		this.fanOn = fanOn;
	}

	@Override
	public void heat(boolean on) {
		heatOn = on;
	}

	@Override
	public void cool(boolean on) {
		coolOn = on;
	}

	@Override
	public void fan(boolean on) {
		fanOn = on;

	}

	@Override
	public int temp() {
		return temp;
	}

	public void setTemp(int temp){
		this.temp = temp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
