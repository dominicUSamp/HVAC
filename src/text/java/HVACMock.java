
public class HVACMock implements HVAC {
	public boolean heatCalled = false;
	public boolean coolCalled = false;
	public boolean fanCalled;
	private int temp;
	
	public HVACMock(int temp) {
		this.temp = temp;
	}

	@Override
	public void heat(boolean on) {
		heatCalled = true;
	}

	@Override
	public void cool(boolean on) {
		coolCalled = true;
	}

	@Override
	public void fan(boolean on) {
		fanCalled = true;

	}

	@Override
	public int temp() {
		return temp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
