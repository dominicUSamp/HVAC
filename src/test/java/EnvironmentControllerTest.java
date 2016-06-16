import org.junit.Assert;
import org.junit.Test;

public class EnvironmentControllerTest {

	HVACMock mock;
	EnvironmentControllerImpl controller;

	@Test
	public void testDefaultTemps() {
		new Given()
			.build();

		Assert.assertEquals(75, controller.getHighTemp());
		Assert.assertEquals(65, controller.getLowTemp());
	}

	@Test
	public void testHeatOnWhenToolCool() {
		new Given()
			.itsCold()
			.build();

		controller.tick();

		Assert.assertEquals(mock.heatOn, true);
		Assert.assertEquals(mock.fanOn, true);
		
	}

	@Test
	public void testCoolOnWhenTooHot() {
		new Given()
			.itsHot()
			.build();

		controller.tick();

		Assert.assertEquals(mock.coolOn, true);
		Assert.assertEquals(mock.fanOn, true);

	}

	@Test
	public void testNothingOnWhenJustRight() {
		new Given()
			.itsOk()
			.build();

		controller.tick();

		Assert.assertEquals(mock.coolOn, false);
		Assert.assertEquals(mock.heatOn, false);
		Assert.assertEquals(mock.fanOn, false);
	}

	@Test
	public void testFanCantStartFor5MinAfterHeaterOff() {
		new Given()
			.itsHot()
			.heatIsOn()
			.build();

		controller.tick();

		Assert.assertFalse(mock.heatOn);
		Assert.assertTrue(mock.coolOn);
		Assert.assertFalse(mock.fanOn);

		for (int i = 0 ; i < 4 ; i++) {
			controller.tick();
			Assert.assertEquals("seeing if fan is on the "+(i+1)+" time", mock.fanOn, false);
		}


		controller.tick();
		Assert.assertEquals(mock.fanOn, true);
	}

	@Test
	public void testFanCantStartFor3MinAfterCoolOff() {
		new Given()
			.itsCold()
			.coolIsOn()
			.build();

		controller.tick();

		Assert.assertTrue(mock.heatOn);
		Assert.assertFalse(mock.coolOn);
		Assert.assertFalse(mock.fanOn);

		for (int i = 0 ; i < 2 ; i++) {
			controller.tick();
			Assert.assertEquals("seeing if fan is on the "+(i+1)+" time", mock.fanOn, false);
		}

		controller.tick();
		Assert.assertEquals(mock.fanOn, true);
	}

	@Test
	public void testFanCooldownShouldNotResetWithCoolAfterHeat() {
		new Given()
			.itsHot()
			.heatIsOn()
			.build();

		controller.tick();

		Assert.assertFalse(mock.heatOn);
		Assert.assertTrue(mock.coolOn);
		Assert.assertFalse(mock.fanOn);

		makeItCold();

		controller.tick();

		Assert.assertTrue(mock.heatOn);

		for (int i = 0 ; i < 3 ; i++) {
			controller.tick();
			Assert.assertEquals("seeing if fan is on the "+(i+1)+" time", mock.fanOn, false);
		}

		controller.tick();
		Assert.assertEquals(mock.fanOn, true);
	}

	@Test
	public void testTurnHeatOffTwiceDoesntResetTimer() {
		new Given()
			.itsHot()
			.heatIsOn()
			.build();

		controller.tick();

		makeItCold();

		controller.tick();

		makeItHot();

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertFalse(mock.fanOn);
		controller.tick();
		Assert.assertFalse(mock.fanOn);
		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertTrue(mock.fanOn);
	}

	@Test
	public void testTurnCoolOffTwiceDoesntResetTimer() {
		new Given()
			.itsCold()
			.coolIsOn()
			.build();

		controller.tick();

		makeItHot();

		controller.tick();

		makeItCold();

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertFalse(mock.fanOn);

		controller.tick();
		Assert.assertTrue(mock.fanOn);
	}

	@Test
	public void testCoolOffWhenJustRight() {
		new Given()
			.itsOk()
			.coolIsOn()
			.build();

		controller.tick();

		Assert.assertFalse(mock.coolOn);
	}

	@Test
	public void testHeatOffWhenJustRight() {
		new Given()
			.itsOk()
			.heatIsOn()
			.build();

		controller.tick();

		Assert.assertFalse(mock.heatOn);
	}

	@Test
	public void testTempGettersAndSetters() {
		new Given()
			.itsOk()
			.heatIsOn()
			.build();

		int lowTemp = 0;
		int highTemp = 2000;
		controller.setHighTemp(highTemp);
		controller.setLowTemp(lowTemp);

		Assert.assertEquals(lowTemp, controller.getLowTemp());
		Assert.assertEquals(highTemp, controller.getHighTemp());
	}


	private void makeItCold(){
		mock.setTemp(controller.getLowTemp() - 1);
	}

	private void makeItHot(){
		mock.setTemp(controller.getHighTemp() + 1);
	}

	private void makeItOk(){
		mock.setTemp(controller.getHighTemp() - 1);
	}

	class Given{
		private int initialTemp = 150;
		private boolean fanOn = false;
		private boolean heatOn = false;
		private boolean coolOn = false;

		public Given itsCold(){
			this.initialTemp = EnvironmentController.initialLowTemp - 1;

			return this;
		}

		public Given itsHot(){
			this.initialTemp = EnvironmentController.initialHighTemp + 1;

			return this;
		}

		public Given itsOk(){
			this.initialTemp = EnvironmentController.initialLowTemp + 1;

			return this;
		}


		public Given fanIsOn(){
			this.fanOn = true;

			return this;
		}

		public Given coolIsOn(){
			this.coolOn = true;

			return this;
		}

		public Given heatIsOn(){
			this.heatOn = true;

			return this;
		}

		public Given build(){
			mock = new HVACMock(this.initialTemp, this.heatOn, this.coolOn, this.fanOn);
			controller = new EnvironmentControllerImpl(mock);

			controller.heatOn = this.heatOn;
			controller.coolOn = this.coolOn;
			controller.fanOn = this.fanOn;

			return this;
		}
	}
}
