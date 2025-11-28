package driver;

import com.codeborne.selenide.WebDriverProvider;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Nonnull;

import config.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmulatorDriver implements WebDriverProvider {
    private String getAbsolutePath(String filePath) {
        File file = new File(filePath);
        assertTrue(file.exists(), filePath + " not found");

        return file.getAbsolutePath();
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        desiredCapabilities.setCapability("platformName", Config.getProperty("platform.name"));

        desiredCapabilities.setCapability("automationName", "UiAutomator2");
        desiredCapabilities.setCapability("deviceName", Config.getProperty("device.name"));

        desiredCapabilities.setCapability("appPackage", Config.getProperty("app.package"));
        desiredCapabilities.setCapability("appActivity", Config.getProperty("app.activity"));

        desiredCapabilities.setCapability("noReset", false);
        desiredCapabilities.setCapability("fullReset", false);
        desiredCapabilities.setCapability("skipDeviceInitialization", false);
        desiredCapabilities.setCapability("skipServerInstallation", false);

        desiredCapabilities.setCapability("app", Config.getProperty("app"));

        try {
            return new AndroidDriver(new URL(Config.getProperty("appium.url")), desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + Config.getProperty("appium.url"));
        }
    }
}
