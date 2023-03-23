package tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.testng.annotations.Test;
import base.AccessibilityBase;
import base.TestBase;

import java.io.IOException;

public class FullPageTest extends TestBase
{
    AccessibilityBase a11y;

    @Test
    public void test() throws IOException {
        webDriver().get("https://www.planviewer.fidelity.co.uk/newlogin/#/");
        a11y.checkAccessibilityViolations();
    }
}
