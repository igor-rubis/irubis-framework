/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.restWebServices

import com.google.appengine.api.urlfetch.HTTPMethod
import com.irubis_framework.steps.Actions
import groovy.json.JsonBuilder
import org.apache.http.HttpHost
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.DefaultProxyRoutePlanner
import org.apache.http.util.EntityUtils
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.allure.annotations.Step
import wslite.json.JSONObject

/**
 * Created by Igor_Rubis, 11/22/2016.
 */

class BaseWebService extends Actions {
    def client
    def usingProxy
    def request
    def requestJSON
    def response
    def responseJSON

    @Step
    def buildHTTPClient() {
        usingProxy = usingProxy ?: System.getProperty('apiProxy')
        if (usingProxy) {
            def proxy = URI.create(usingProxy)
            def routePlanner = new DefaultProxyRoutePlanner(new HttpHost(proxy.getHost(), proxy.getPort()))
            client = HttpClients.custom()
                    .setRoutePlanner(routePlanner)
                    .build()
        } else {
            client = HttpClientBuilder.create().build()
        }
    }

    @Step
    def initConnection(url, HTTPMethod httpMethod) {
        request = Eval.me("return new org.apache.http.client.methods.Http${httpMethod.name()}(${url})")
    }

    @Step
    def initRequestBody(closure) {
        requestJSON = new JSONObject()
        closure()
        def input = new StringEntity(requestJSON as String)
        input.setContentType("application/json")
        (request as HttpPost).setEntity(input)
    }

    @Step
    def setHeaders(headers) {
        headers.each { key, value ->
            request.setHeader(key, value)
        }
    }

    @Step
    def analyzeResponseStatusCode(closure = null) {
        response = client.execute(request)
        if (closure) {
            responseJSON = new JSONObject(EntityUtils.toString(response.getEntity()))
            closure()
        }
    }

    @Attachment(value = 'Request and response info', type = 'application/json')
    def dumpRequestResponseInfo() {
        def info = [
                request : [
                        request_method : request.method,
                        request_url    : request.URI.toString(),
                        request_headers: request.headergroup.headers.collect { it.toString() },
                        request_json   : requestJSON ?: 'No request json'
                ],
                response: [
                        response_status_code: "${response.statusLine.statusCode} '${response.statusLine.reasonPhrase}'",
                        response_headers    : response.original.headergroup.headers.collect {
                            it.toString()
                        },
                        response_body       : responseJSON ?: responseBody
                ]
        ]

        def stringifiedInfo
        try {
            stringifiedInfo = new JsonBuilder(info).toPrettyString()
        } catch (OutOfMemoryError error) {
            stringifiedInfo = "Could not dump request and response info due to error: ${error.message}"
        }
        return stringifiedInfo
    }
}