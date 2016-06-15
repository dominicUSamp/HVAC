import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testHeatOnWhenToolCool() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.TOO_COOL.value));
		cont.tick();
		Assert.assertEquals(cont.heatOn, true);
		Assert.assertEquals(cont.fanOn, true);
		
	}
	
	@Test
	public void testCoolOnWhenTooHot() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.TOO_HOT.value));
		cont.tick();
		Assert.assertEquals(cont.coolOn, true);
		Assert.assertEquals(cont.fanOn, true);
		
	}
	
	@Test
	public void testNothingOnWhenJustRight() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
		
		cont.tick();
		Assert.assertEquals(cont.coolOn, false);
		Assert.assertEquals(cont.heatOn, false);
		Assert.assertEquals(cont.fanOn, false);
	}
	
	@Test
	public void testFanCantStartFor5MinAfterHeaterOff() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
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
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
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
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
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
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
		cont.fan(true);
		cont.heat(false);
		
		cont.fan(false);
		Assert.assertEquals(cont.fanOn, false);
	}
	
	@Test
	public void testTurnHeatOffTwiceDoesntResetTimer() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
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
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
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
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
		cont.cool(true);
		
		cont.tick();
		Assert.assertEquals(cont.coolOn, false);
	}
	
	@Test
	public void testHeatOffWhenJustRight() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));
		cont.heat(true);
		
		cont.tick();
		Assert.assertEquals(cont.heatOn, false);
	}

	@Test
	public void testTempGettersAndSetters() {
		EnvironmentControllerImpl cont =
				new EnvironmentControllerImpl(new HVACMock(EnvironmentControllerImpl.TemperatureValues.JUST_RIGHT.value));

		int lowTemp = 100;
		int highTemp = 200;
		cont.setHighTemp(highTemp);
		cont.setLowTemp(lowTemp);

		Assert.assertEquals(lowTemp, cont.getLowTemp());
		Assert.assertEquals(highTemp, cont.getHighTemp());
	}
}
