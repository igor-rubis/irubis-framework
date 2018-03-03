package com.irubis_framework.helpers.jvmProperties

class JVMProperties {
    static BROWSER = System.getProperty('browser', 'firefox')
    static API_PROXY = System.getProperty('apiProxy')
    static URL = System.getProperty('url')
    static TESTS_MODE = System.getProperty('testsMode', 'local')
    static UI_HUB_URL = System.getProperty('uiHubUrl')
    static MOBILE_HUB_URL = System.getProperty('mobileHubUrl')
    static CURRENT_FOLDER = System.getProperty("user.dir")
    static TESTS_FOLDER = System.getProperty('testsFolder')
    static POLLING_INTERVAL = System.getProperty('pollingInterval', '500') as Integer
    static ELECTRON_BINARY = System.getProperty('electronBinary')
}