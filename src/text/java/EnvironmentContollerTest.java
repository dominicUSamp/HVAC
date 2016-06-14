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

//	@Test
//	public void test() {
//		EnvironmentController cont = new EnvironmentController();
//		Assert.assertEquals(3, 3);
//	}
	
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
		cont.tick();
		cont.fan(true);
		Assert.assertEquals(((HVACMock)cont.getHvac()).fanCalled, false);
	}
	
//TODO make sure we can turn fan off 
}
