import java.lang.IllegalArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class EnvironmentCommandManagerTest {

	@Test
	public void testParseForSettingHighTemp() throws IllegalArgumentException {
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int temp = 500;
		String message = "highTemp:"+temp+"";
		manager.setTemp(message);

		assertEquals(temp,ecMock.highTempCalledWith);
	}

	@Test
	public void testParseForSettingLowTemp() throws IllegalArgumentException {
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int temp = 10;
		String message = "lowTemp:"+temp+"";
		manager.setTemp(message);

		assertEquals(temp,ecMock.lowTempCalledWith);
	}

	@Test
	public void testParseForSettingBothTemp() throws IllegalArgumentException {
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		int highTemp = 500;
		int lowTemp = 10;
		String message = "highTemp:"+highTemp+",lowTemp:"+lowTemp;
		manager.setTemp(message);

		assertEquals(highTemp,ecMock.highTempCalledWith);
		assertEquals(lowTemp,ecMock.lowTempCalledWith);
	}

	@Test
	public void testOverridingInitialTemps(){
		int initialHighTemp = 100;
		int initialLowTemp = 0;

		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock, initialHighTemp, initialLowTemp);

		assertEquals(0,ecMock.lowTempCalledWith);
		assertEquals(100,ecMock.highTempCalledWith);
	}

	@Test
	public void testHighTempMustBeSetTo5DegreesHigherThanLow() throws IllegalArgumentException {
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		ecMock.lowTemp = 10;

		try {
			manager.setTemp("highTemp:12");
		}catch(IllegalArgumentException e){
			assertEquals(
					"Error should be thrown with correct message",
					"High Temperature should be at least 5 degrees warmer than low. Low temp set to "+ecMock.lowTemp+".",
					e.getMessage()
			);

			return;
		}

		fail("Should have thrown and exception to pass");
	}

	@Test
	public void testLowTempMustBeSetTo5DegreesHigherThanHigh() throws IllegalArgumentException {
		EnvironmentControllerMock ecMock = new EnvironmentControllerMock();
		EnvironmentCommandManager manager = new EnvironmentCommandManager(ecMock);

		ecMock.highTemp = 10;

		try {
			manager.setTemp("lowTemp:9");
		}catch(IllegalArgumentException e){
			assertEquals(
					"Error should be thrown with correct message",
					"Low Temperature should be at least 5 degrees cooler than high. High temp set to "+ecMock.highTemp+".",
					e.getMessage()
			);

			return;
		}

		fail("Should have thrown and exception to pass");
	}

}
