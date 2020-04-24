import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;


/**
 * @author 314652
 *
 */

public class Test6 {

	@Test
	public void Test6() throws MalformedURLException {
		System.out.println("Inside Test6()...");
		
		URL url = new URL("http://localhost:4444/wd/hub");
		
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		
		RemoteWebDriver driver = new RemoteWebDriver(url, caps);
		
		driver.get("http://www.paypal.com");
		System.out.println(driver.getTitle());
	}

}
