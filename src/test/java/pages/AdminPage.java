package pages;

import com.codeborne.selenide.Condition;
import io.appium.java_client.MobileBy;

import static com.codeborne.selenide.Selenide.$;

public class AdminPage {
    private static final String LOGIN_TITLE = "//android.widget.TextView[@text='Admin Panel']";

    public AdminPage checkAdminPanelLoaded() {
        $(MobileBy.xpath(LOGIN_TITLE)).should(Condition.visible);
        return this;
    }
}
