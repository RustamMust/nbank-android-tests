package tests;

import static io.qameta.allure.Allure.step;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;

import io.qameta.allure.Allure;
import models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import config.Config;
import driver.EmulatorDriver;
import helpers.ApiHelper;
import helpers.ScriptHelper;
import io.qameta.allure.selenide.AllureSelenide;

public class BaseTest {
    private static final String START_EMULATOR_SCRIPT_NAME = "start-emulator.sh";
    private static final String STOP_EMULATOR_SCRIPT_NAME = "stop-emulator.sh";
    private static final String ANDROID_HOME = "ANDROID_HOME";

    protected User ADMIN = User.builder().username("admin").password("admin").build();

    @BeforeAll
    public static void setup() {
        ApiHelper.healthCheck();

        ScriptHelper.putEnvVariable(ANDROID_HOME, Config.getProperty("android.home"));
        ScriptHelper.execute(START_EMULATOR_SCRIPT_NAME);

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = EmulatorDriver.class.getName();
        Configuration.browserSize=null;
        Configuration.timeout = 6000;
    }

    @BeforeEach
    public void startDriver() {
        step("Open driver", () -> Selenide.open());
    }

    @AfterEach
    public void stopDriver() {
        step("Close driver", Selenide::closeWebDriver);
    }

    @AfterAll
    public static void clean() {
        ScriptHelper.execute(STOP_EMULATOR_SCRIPT_NAME);
    }
}
