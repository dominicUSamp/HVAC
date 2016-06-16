/**
 * Created by dominicscimeca on 6/15/16.
 */
public interface EnvironmentController {
	int initialHighTemp = 75;
	int initialLowTemp = 65;

	void setHighTemp(int temp);

	void setLowTemp(int lowTemp);

	int getHighTemp();

	int getLowTemp();
}
