import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;


/**
 * @author 314652
 *
 */
public class Test5 {

	@Test
	public void Test5() throws MalformedURLException {
		System.out.println("Inside Test5()...");
		
		URL url = new URL("http://localhost:4444/wd/hub");
		
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		
		RemoteWebDriver driver = new RemoteWebDriver(url, caps);
		
		driver.get("http://www.linkedin.com");
		System.out.println(driver.getTitle());
	}

}
