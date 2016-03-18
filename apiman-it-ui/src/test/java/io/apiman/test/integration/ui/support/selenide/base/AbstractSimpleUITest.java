package io.apiman.test.integration.ui.support.selenide.base;

import io.apiman.test.integration.runner.ApimanRunner;
import io.apiman.test.integration.ui.support.selenide.SelenideUtils;

import com.codeborne.selenide.junit.ScreenShooter;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author jkaspar
 */
@RunWith(ApimanRunner.class)
public abstract class AbstractSimpleUITest {

    @Rule
    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

    @BeforeClass
    public static void setUpWebDriverEventListener() throws Exception {
        SelenideUtils.setEventListener();
    }
}
