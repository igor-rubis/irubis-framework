package ui

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.helpers.testing.test.ui.BaseUiTest
import org.junit.Test

class ExampleUiTest extends BaseUiTest {
    @Test
    void failingTest() {
        Browser.instance.get('https://www.google.com/')
        assert (Browser.instance.getTitle() == '#######')
    }

    @Test
    void passingTest() {
        Browser.instance.get('https://www.google.com/')
        assert (Browser.instance.getTitle() == 'Google')
    }

    @Test
    void errorTest() {
        Browser.instance.get('https://www.google.com/')
        throw new RuntimeException('smth went wrong!')
    }
}