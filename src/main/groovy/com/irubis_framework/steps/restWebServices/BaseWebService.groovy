/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.restWebServices

import com.google.appengine.api.urlfetch.HTTPMethod
import com.irubis_framework.helpers.systemProp.SystemProp
import com.irubis_framework.steps.Actions
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import org.apache.http.HttpHost
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.DefaultProxyRoutePlanner
import org.apache.http.util.EntityUtils
import org.hamcrest.Matcher
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.allure.annotations.Step
import wslite.json.JSONException
import wslite.json.JSONObject

import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by Igor_Rubis, 11/22/2016.
 */

class BaseWebService extends Actions {
    def client
    def request
    def requestJSON
    def response
    def responseJSON
    def responseBody

    @Step
    def buildHTTPClient() {
        if (SystemProp.API_PROXY) {
            def proxy = URI.create(SystemProp.API_PROXY)
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

    def analyzeResponseStatusCode(Matcher matcher, closure = null) {
        analyzeResponseStatusCode(matcher, null, closure)
    }

    @Step
    def analyzeResponseStatusCode(Matcher matcher, HttpClientContext context, closure = null) {
        response = context ? client.execute(request, context) : client.execute(request)
        responseBody = response.getEntity() ? EntityUtils.toString(response.getEntity()) : 'No response body'
        try {
            responseJSON = new JSONObject(responseBody)
        } catch (JSONException ignored) {
            responseJSON = null
        }
        try {
            dumpRequestResponseInfo()
            if (closure) {
                closure()
            }
            assertThat("Unexpected response status code. Response body: ${responseBody}", response.statusLine.statusCode, matcher)
        } catch (Throwable ex) {
            dumpCurrentSession()
            !context ?: dumpHttpClientContext(context)
            throw ex
        }
    }

    @Attachment(value = 'Request and response info', type = 'application/json')
    def dumpRequestResponseInfo() {
        def info = [
                request : [
                        request_method : request.method,
                        request_url    : request.URI as String,
                        request_headers: request.headergroup.headers.collect { header -> header as String },
                        request_json   : requestJSON ?: 'No request json'
                ],
                response: [
                        response_status_code: "${response.statusLine.statusCode} '${response.statusLine.reasonPhrase}'",
                        response_headers    : response.original.headergroup.headers.collect { header -> header as String },
                        response_body       : responseJSON ?: responseBody
                ]
        ]

        def stringedInfo
        try {
            stringedInfo = new JsonBuilder(info).toPrettyString()
        } catch (OutOfMemoryError error) {
            stringedInfo = "Could not dump request and response info due to error: ${error.message}"
        }
        return stringedInfo
    }

    @Attachment(value = 'Http client context', type = 'application/json')
    static dumpHttpClientContext(context) {
        JsonOutput.prettyPrint(
                JsonOutput.toJson(
                        context.properties.collectEntries { key, value ->
                            [(key): value as String]
                        }
                )
        )
    }
}