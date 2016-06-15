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
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.TOO_COOL.value));
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).heatCalled, true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
		
	}
	
	@Test
	public void testCoolOnAbove75() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.TOO_HOT.value));
		cont.tick();
		Assert.assertEquals(((HVACMock)cont.getHvac()).coolCalled, true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
		
	}
	
//	@Test
//	public void testNothingOn65To75() {
//		EnvironmentController cont = 
//				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
//		
//		cont.tick();
//		Assert.assertEquals(((HVACMock)cont.getHvac()).coolCalled, false);
//		Assert.assertEquals(((HVACMock)cont.getHvac()).heatCalled, false);
//		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
//	}
	
	@Test
	public void testFanCantStartFor5MinAfterHeaterOff() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
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
	public void testFanCantStartFor3MinAfterCoolOff() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.cool(true);
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
	public void testFanCantStartAfterHeaterAndCoolOff() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
		
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
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.fan(true);
		cont.heat(false);
		
		cont.fan(false);
		Assert.assertEquals(cont.fanOn, false);
	}
	
	@Test
	public void testTurnHeatOffTwiceDoesntResetTimer() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
		
		cont.heat(false);
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.heat(false);
		cont.fan(true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
	
	@Test
	public void testTurnCoolOffTwiceDoesntResetTimer() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.cool(true);
		
		cont.cool(false);
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.tick();
		cont.cool(false);
		cont.fan(true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, true);
	}
	
	@Test
	public void testCoolOffWhenJustRight() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.cool(true);
		
		cont.tick();
		Assert.assertEquals(cont.coolOn, false);
	}
	
	@Test
	public void testHeatOffWhenJustRight() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
		
		cont.tick();
		Assert.assertEquals(cont.heatOn, false);
	}
}
