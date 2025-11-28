package tests;

import org.junit.jupiter.api.Test;

import pages.LoginPage;

public class BankTests extends BaseTest {
    @Test
    public void userCanLogin() {
        new LoginPage()
                .enterUsername(ADMIN.getUsername())
                .enterPassword(ADMIN.getPassword())
                .clickLoginButton()
                .checkAdminPanelLoaded();
    }
}
