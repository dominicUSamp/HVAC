import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentContollerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testHeatOnBelow65() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(64));
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).heatCalled, true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
		
	}
	
	@Test
	public void testCoolOnAbove75() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(76));
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).coolCalled, true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
		
	}
	
	@Test
	public void testNothingOn65To75() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(70));
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).coolCalled, false);
		Assert.assertEquals(((HVACMock)cont.getHvac()).heatCalled, false);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
	}
	
	@Test
	public void testFanCantStartFor5MinAfterHeaterOff() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(70));
		cont.heat(false);
		for (int i = 0 ; i < 5 ; i++) {
			cont.fan(true);
			cont.tick();
			Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
		}
		cont.fan(true);
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
	
	@Test
	public void testFanCantStartFor3MinAfterCoolerOff() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(70));
		cont.cool(false);
		for (int i = 0 ; i < 3 ; i++) {
			cont.fan(true);
			cont.tick();
			Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
		}
		cont.fan(true);
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
	
	@Test
	public void testFanCantStartAfterHeaterAndCoolerTurnOff() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(70));
		cont.heat(false);
		cont.cool(false);
		for (int i = 0 ; i < 5 ; i++) {
			cont.fan(true);
			cont.tick();
			Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
		}
		cont.fan(true);
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
	
	@Test
	public void testTurnFanOffDuringCoolDown() {
		EnvironmentController cont = new EnvironmentController(new HVACMock(70));
		cont.heat(false);
		cont.fan(false);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
}
