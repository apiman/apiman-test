package io.apiman.test.integration.ui.support.selenide.base;

import io.apiman.test.integration.base.AbstractApiTest;
import io.apiman.test.integration.ui.support.selenide.SelenideUtils;

import com.codeborne.selenide.junit.ScreenShooter;
import org.junit.BeforeClass;
import org.junit.Rule;

/**
 * @author jkaspar
 */
public abstract class AbstractApiUITest extends AbstractApiTest {

    @Rule
    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @BeforeClass
    public static void setUpWebDriverEventListener() throws Exception {
        SelenideUtils.setEventListener();
    }
}
