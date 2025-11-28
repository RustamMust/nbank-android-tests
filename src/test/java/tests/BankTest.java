package tests;

import org.junit.jupiter.api.Test;

import pages.LoginPage;

public class BankTest extends BaseTest {
    @Test
    public void userCanLogin() {
        new LoginPage()
                .enterUsername(ADMIN.getUsername())
                .enterPassword(ADMIN.getPassword())
                .clickLoginButton()
                .checkAdminPanelLoaded();
    }
}
