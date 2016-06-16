import org.junit.Test;

/**
 * Created by dominicscimeca on 6/15/16.
 */
public class SocketWrapperTest {

	@Test
	public void testSocketWrapperExists(){
		new SocketWrapper(new EnvironmentCommandManager(new EnvironmentController() {
			@Override
			public void setHighTemp(int temp) {

			}

			@Override
			public void setLowTemp(int lowTemp) {

			}

			@Override
			public int getHighTemp() {
				return 0;
			}

			@Override
			public int getLowTemp() {
				return 0;
			}
		}));
	}
}
