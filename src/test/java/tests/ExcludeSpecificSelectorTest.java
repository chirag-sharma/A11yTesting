package tests;

import base.AccessibilityBase;
import base.TestBase;
import org.testng.annotations.Test;

import java.io.IOException;

public class ExcludeSpecificSelectorTest extends TestBase {
    AccessibilityBase a11y;

    @Test
    public void test() throws IOException {
        webDriver().get("https://www.icicibank.com/");
        a11y.checkAccessibilityExcludingSelector("ul");
    }
}
