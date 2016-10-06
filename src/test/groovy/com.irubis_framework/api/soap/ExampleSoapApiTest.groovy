package com.irubis_framework.api.soap

import com.irubis_framework.api.BaseApiTest
import com.irubis_framework.steps.apiSteps.ApiSteps
import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.comparesEqualTo
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase

/**
 * Created by Igor_Rubis. 10/6/16.
 */

@Description("This is an example test suite")
class ExampleSoapApiTest extends BaseApiTest {

    @Override
    protected service() {
        return '/Holidays/US/Dates/USHolidayDates.asmx?WSDL'
    }

    @Title("Soap test")
    @Test
    void testMLKDay() {
        def baseNS = 'http://www.27seconds.com/Holidays/US/Dates/'
        action = "${baseNS}GetMartinLutherKingDay"

        def steps = new ApiSteps()
        response = steps.getResponse(client, action) {
            body {
                GetMartinLutherKingDay('xmlns': baseNS) {
                    year(2016)
                }
            }
        }

        def date = Date.parse("yyyy-MM-dd'T'hh:mm:ss", response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

        steps.compareResults(date, comparesEqualTo(Date.parse('yyyy-MM-dd', '2016-01-15')))
        steps.compareResults(response.httpResponse.headers['X-Powered-By'].value as String, equalToIgnoringCase('ASP.NET'))
    }

    @Title("Failing Soap test")
    @Test
    void testMLKDayFail() {
        def baseNS = 'http://www.27seconds.com/Holidays/US/Dates/'
        action = "${baseNS}GetMartinLutherKingDay"

        def steps = new ApiSteps()
        response = steps.getResponse(client, action) {
            body {
                GetMartinLutherKingDay('xmlns': baseNS) {
                    year(2016)
                }
            }
        }

        def date = Date.parse("yyyy-MM-dd'T'hh:mm:ss", response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

        steps.compareResults(date, comparesEqualTo(Date.parse('yyyy-MM-dd', '2013-01-15')))
        steps.compareResults(response.httpResponse.headers['X-Powered-By'].value as String, equalToIgnoringCase('ASP.NET'))
    }
}
