package com.irubis_framework.helpers.systemProp

class SystemProp {
    static String BROWSER = System.getProperty('browser')
    static String TESTS_MODE = System.getProperty('testsMode')
    static String MOBILE_HUB_URL = System.getProperty('mobileHubUrl')
    static String UI_HUB_URL = System.getProperty('uiHubUrl')
    static String ELECTRON_BINARY = System.getProperty('electronBinary')
    static String USER_DIR = System.getProperty('user.dir')
    static String TESTS_FOLDER = System.getProperty('testsFolder')
    static Integer WAITING_INTERVAL = System.getProperty('waitingInterval', '15000') as Integer
    static Integer POLLING_INTERVAL = System.getProperty('pollingInterval', '500') as Integer
    static String API_PROXY = System.getProperty('apiProxy')
    static String API_URL = System.getProperty('apiUrl')
}