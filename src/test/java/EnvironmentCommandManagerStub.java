import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dominicscimeca on 6/16/16.
 */
public class EnvironmentCommandManagerStub extends EnvironmentCommandManager {
	private CountDownLatch latch;
	String setTempCalledWith;
	boolean tempCalled;

	public EnvironmentCommandManagerStub(CountDownLatch latch){
		super(new EnvironmentControllerMock());
		this.latch = latch;
	}

	public EnvironmentCommandManagerStub(EnvironmentController controller) {
		super(controller);
	}

	@Override
	public void setTemp(String message) throws IllegalArgumentException {
		this.latch.countDown();
		this.setTempCalledWith = message;
		this.tempCalled = true;
	}
}
