/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.restWebServices

import com.google.appengine.api.urlfetch.HTTPMethod
import groovy.json.JsonBuilder
import org.apache.http.HttpHost
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.DefaultProxyRoutePlanner
import org.apache.http.util.EntityUtils
import ru.yandex.qatools.allure.annotations.Attachment
import wslite.json.JSONObject

import java.util.logging.Logger

/**
 * Created by Igor_Rubis, 11/22/2016.
 */

class BaseWebService {
    def log = Logger.getAnonymousLogger()
    def client
    def usingProxy
    def request
    def requestJSON
    def response
    def responseJSON

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

    def initConnection(url, HTTPMethod httpMethod) {
        request = Eval.me("return new org.apache.http.client.methods.Http${httpMethod.name()}(${url})")
    }

    def initRequestBody(closure) {
        requestJSON = new JSONObject()
        closure()
        def input = new StringEntity(requestJSON as String)
        input.setContentType("application/json")
        (request as HttpPost).setEntity(input)
    }

    def setHeaders(headers) {
        headers.each { key, value ->
            request.setHeader(key, value)
        }
    }

    def analyzeResponseStatusCode(closure = null) {
        log.info("Request:  ${request}")
        response = client.execute(request)
        log.info("Response: ${response}")
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