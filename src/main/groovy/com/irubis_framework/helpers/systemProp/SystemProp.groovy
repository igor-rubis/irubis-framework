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
    static Boolean IGNORE_SSL_CERT_VALIDATION = System.getProperty('ignoreSslCertValidation', 'false').toBoolean()
    static ArrayList<String> CHROME_OPTIONS = System.getProperty('chromeOptions', '').split(',')
    static ArrayList<String> EXCLUDE_SWITCHES = System.getProperty('excludeSwitches', '').split(',')
    static Boolean ALLOW_ALL_HOSTS = System.getProperty('allowAllHosts', 'false').toBoolean()
    static Boolean WEBDRIVER_NAVIGATOR_UNDEFINED = System.getProperty('webdriverNavigatorUndefined', 'false').toBoolean()
    static Boolean TURN_OFF_USE_AUTOMATION_EXTENSION = System.getProperty('turnOffUseAutomationExtension', 'false').toBoolean()
}