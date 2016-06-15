import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class EnvironmentCommandManagerTest {

	@Test
	public void testParseForSettingHighTemp(){
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int temp = 500;
		String message = "highTemp:"+temp+"";
		manager.setTemp(message);

		assertEquals(temp,ecMock.highTempCalledWith);
	}

	@Test
	public void testParseForSettingLowTemp(){
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int temp = 100;
		String message = "lowTemp:"+temp+"";
		manager.setTemp(message);

		assertEquals(temp,ecMock.lowTempCalledWith);
	}

	@Test
	public void testParseForSettingBothTemp(){
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int highTemp = 500;
		int lowTemp = 100;
		String message = "highTemp:"+highTemp+",lowTemp:"+lowTemp;
		manager.setTemp(message);

		assertEquals(highTemp,ecMock.highTempCalledWith);
		assertEquals(lowTemp,ecMock.lowTempCalledWith);
	}

	@Test
	public void testInitialTemps(){
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		assertEquals(65,manager.getInitialLowTemp());
		assertEquals(75,manager.getInitialHighTemp());
	}

	@Test
	public void testOverridingInitialTemps(){
		int initialHighTemp = 100;
		int initialLowTemp = 0;

		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock, initialHighTemp, initialLowTemp);

		assertEquals(0,manager.getInitialLowTemp());
		assertEquals(100,manager.getInitialHighTemp());
	}

//	@Test
//	public void testHighTempHasToBe5DegreesWarmerThanLow(){
//		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
//		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock, initialHighTemp, initialLowTemp);
//	}

}
