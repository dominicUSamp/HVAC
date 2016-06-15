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
	public void testHeatOnWhenToolCool() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.TOO_COOL.value));
		cont.tick();
		Assert.assertEquals(cont.heatOn, true);
		Assert.assertEquals(cont.fanOn, true);
		
	}
	
	@Test
	public void testCoolOnWhenTooHot() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.TOO_HOT.value));
		cont.tick();
		Assert.assertEquals(cont.coolOn, true);
		Assert.assertEquals(cont.fanOn, true);
		
	}
	
	@Test
	public void testNothingOnWhenJustRight() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		
		cont.tick();
		Assert.assertEquals(cont.coolOn, false);
		Assert.assertEquals(cont.heatOn, false);
		Assert.assertEquals(cont.fanOn, false);
	}
	
	@Test
	public void testFanCantStartFor5MinAfterHeaterOff() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
		cont.heat(false);
		
		for (int i = 0 ; i < 5 ; i++) {
			cont.fan(true);
			Assert.assertEquals(cont.fanOn, false);
			cont.tick();
		}
		cont.fan(true);
		Assert.assertEquals(cont.fanOn, true);
	}
	
	@Test
	public void testFanCantStartFor3MinAfterCoolOff() {
		EnvironmentController cont = 
				new EnvironmentController(new HVACMock(EnvironmentController.TemperatureValues.JUST_RIGHT.value));
		cont.cool(true);
		cont.cool(false);
		
		for (int i = 0 ; i < 3 ; i++) {
			cont.fan(true);
			Assert.assertEquals(cont.fanOn, false);
			cont.tick();
		}
		cont.fan(true);
		Assert.assertEquals(cont.fanOn, true);
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
			Assert.assertEquals(cont.fanOn, false);
			cont.tick();
		}
		cont.fan(true);
		Assert.assertEquals(cont.fanOn, true);
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
		Assert.assertEquals(cont.fanOn, true);
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
		Assert.assertEquals(cont.fanOn, true);
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