package ui_automation.uber.map_listener;

import com.codeborne.selenide.WebDriverProvider;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BmpChrome implements WebDriverProvider {
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        System.setProperty("webdriver.chrome.driver","C:\\Users\\mykhailo.mikus\\.m2\\repository\\webdriver\\chromedriver\\win32\\71.0.3578.137\\chromedriver.exe");
        capabilities.setCapability(CapabilityType.PROXY,
                ClientUtil.createSeleniumProxy(Bmp.proxyServer));
        return new ChromeDriver(capabilities);
    }
}
