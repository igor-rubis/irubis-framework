package com.irubis_framework.steps.apiSteps

import com.irubis_framework.steps.Steps
import ru.yandex.qatools.allure.annotations.Step
import wslite.soap.SOAPClient

import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis. 10/6/16.
 */
class ApiSteps extends Steps {

    @Step
    def getSoapClient(url, service) {
        eventually {
            return new SOAPClient("${url}${service}")
        }
    }

    @Step
    def getResponse(client, action, closure) {
        eventually {
            client.send(SOAPAction: action, closure)
        }
    }

    @Step
    def compareResults(actual, expected, message=null) {
        eventually {
            assertThat(message, actual, expected)
        }
    }
}
