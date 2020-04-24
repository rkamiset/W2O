import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

/**
 * @author 314652
 *
 */
public class Test2 {

	@Test
	public void Test2() throws MalformedURLException {
		System.out.println("Inside Test2()...");
		
		URL url = new URL("http://localhost:4444/wd/hub");
		
		DesiredCapabilities caps = DesiredCapabilities.firefox();
		
		RemoteWebDriver driver = new RemoteWebDriver(url, caps);
		
		driver.get("http://www.firefox.com");
		System.out.println(driver.getTitle());
	}

}
