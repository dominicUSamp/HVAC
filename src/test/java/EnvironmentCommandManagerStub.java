/**
 * Created by dominicscimeca on 6/16/16.
 */
public class EnvironmentCommandManagerStub extends EnvironmentCommandManager {
	public EnvironmentCommandManagerStub(){
		super(new EnvironmentControllerMock());
	}

	public EnvironmentCommandManagerStub(EnvironmentController controller) {
		super(controller);
	}
}
