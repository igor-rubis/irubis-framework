/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.api.soap

import com.irubis_framework.api.BaseApiTest
import org.junit.Before
import org.junit.Test
import ru.yandex.qatools.allure.annotations.Description
import ru.yandex.qatools.allure.annotations.Title
import wslite.soap.SOAPClient

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.comparesEqualTo
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase

/**
 * Created by Igor_Rubis. 10/6/16.
 */

@Description("This is an example test suite")
class ExampleSOAPApiServiceTest extends BaseApiTest {
    def service = '/Holidays/US/Dates/USHolidayDates.asmx?WSDL'
    def baseNS = 'http://www.27seconds.com/Holidays/US/Dates/'
    def client

    @Before
    def void createClient() {
        client = new SOAPClient("${BASE_URL}${service}")
    }

    @Title("Soap test")
    @Test
    void testMLKDay() {
        def response = client.send(SOAPAction: "${baseNS}GetMartinLutherKingDay") {
            body {
                GetMartinLutherKingDay('xmlns': baseNS) {
                    year(2013)
                }
            }
        }
        def date = Date.parse("yyyy-MM-dd'T'hh:mm:ss", response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

        assertThat(date, comparesEqualTo(Date.parse('yyyy-MM-dd', '2013-01-15')))
        assertThat(response.httpResponse.headers['X-Powered-By'] as String, equalToIgnoringCase('ASP.NET'))
    }

    @Title("Soap test xml")
    @Test
    void testMLKDay2() {
        def response = client.send("""<?xml version='1.0' encoding='UTF-8'?>
                                      <soapenv:Envelope
                                      xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'
                                      xmlns:dat='http://www.27seconds.com/Holidays/US/Dates/'>
                                          <soapenv:Header/>
                                          <soapenv:Body>
                                              <dat:GetMartinLutherKingDay>
                                                 <dat:year>2013</dat:year>
                                              </dat:GetMartinLutherKingDay>
                                          </soapenv:Body>
                                      </soapenv:Envelope>""")

        def date = Date.parse("yyyy-MM-dd'T'hh:mm:ss", response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

        assertThat(date, comparesEqualTo(Date.parse('yyyy-MM-dd', '2013-01-15')))
        assertThat(response.httpResponse.headers['X-Powered-By'], equalToIgnoringCase('ASP.NET'))
    }

    @Title("Soap test junit assertions")
    @Test
    void testMDay() {
        def response = client.send(SOAPAction:'http://www.27seconds.com/Holidays/US/Dates/GetMothersDay') {
            body {
                GetMothersDay('xmlns':'http://www.27seconds.com/Holidays/US/Dates/') {
                    year(2011)
                }
            }
        }

        assertThat(response.GetMothersDayResponse.GetMothersDayResult.text(), comparesEqualTo("2011-05-08T00:00:00"))
        assertThat(response.httpResponse.statusCode, equalTo(200))
        assertThat(response.httpResponse.headers['X-Powered-By'], equalToIgnoringCase('ASP.NET'))
    }
}
