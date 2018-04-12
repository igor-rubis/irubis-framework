package com.irubis_framework.helpers.systemProp

class SystemProp {
    static BROWSER = System.getProperty('browser')
    static TESTS_MODE = System.getProperty('testsMode')
    static MOBILE_HUB_URL = System.getProperty('mobileHubUrl')
    static UI_HUB_URL = System.getProperty('uiHubUrl')
    static ELECTRON_BINARY = System.getProperty('electronBinary')
    static USER_DIR = System.getProperty('user.dir')
    static TESTS_FOLDER = System.getProperty('testsFolder')
    static WAITING_INTERVAL = System.getProperty('waitingInterval', '15000') as Integer
    static POLLING_INTERVAL = System.getProperty('pollingInterval', '500') as Integer
    static API_PROXY = System.getProperty('apiProxy')
}
