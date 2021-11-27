# Testing automation framework based on Selenium

## Add to your project as a dependency to the build.gradle file
```
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.igor-rubis:irubis-framework:2.0.0'
}
```
> To init tag in the repo go to https://jitpack.io/com/github/igor-rubis/irubis-framework/3.0.0

## JVM options:
Folder for allure test results:
* allure.results.directory (`build/reports/allure`)

Running tests locally, in selenium grid or on mobile device:
* testsMode (`mobile` || `remote` || `local`)

Browser options:
* browser (`firefox` || `chrome` || [jBrowser](https://github.com/MachinePublishers/jBrowserDriver) || [electron](https://github.com/electron))

Proxy url for API calls:
* apiProxy

Path to electron app executable for test against astilectron app:
* electronBinary

Waiting interval for tests:
* pollingInterval (`500`)
* waitingInterval (`15000`)

Urls for selenium grid or appium hub:
* uiHubUrl
* mobileHubUrl

Which tests folder to split for parallel test run:
* testsFolder

## Install allure ubuntu:
```https://docs.qameta.io/allure/#_installing_a_commandline```

## Usage
##### Adding main package to test context
```
compile files("${projectDir}/src/main/groovy")
```

##### Splitting tests
```
task splitTests(dependsOn: 'classes', type: JavaExec) {
    main = 'com.irubis_framework.helpers.scripts.SplitTests'
    classpath = sourceSets.main.runtimeClasspath
    systemProperties(System.properties as Map)
}
```

##### Running tests
```
task runAnyTest(type: Test) {
    include "**/testSuites/**/${System.getProperty('currentTest', '*')}.*"
}

new HashSet(tasks.withType(Test)).each { task ->
    task.dependsOn(clean)
    task.ignoreFailures(true)
    task.useJUnit()
    task.systemProperties(System.properties)
    task.jvmArgs "-javaagent:${configurations.agent.singleFile}"
    task.testLogging.showStandardStreams = System.getProperty('debug', 'false').toBoolean()
    task.testLogging.exceptionFormat = 'full'
    task.testLogging.showExceptions = true
    task.maxHeapSize = "2048m"

    task.finalizedBy(generateAllureReport)
}
```

##### Report generation
###### Required configuration
```
configurations {
    agent
}

dependencies {
    agent "org.aspectj:aspectjweaver:1.8.13"
}
```
###### Task to open allure report locally
```
task openAllureReport(type: Exec) {
    executable '/usr/bin/allure'
    args('report', 'open')
}
```
Or `args('open')`  for allure 2

###### Task to generate report from Allure test results
```
task generateAllureReport(type: Exec) {
    executable '/usr/bin/allure'
    args('generate', 'build/reports/allure')
}
```

Or `args('generate', '--clean', 'build/reports/allure')` for allure 2, which requires '--clean' parameter

##### Classes inheritance

Selenium tests -> `BaseUiTest`

Page level steps classes -> `PageSteps`

Rest web services classes -> `BaseWebService`

User actions level classes -> `WebUiActions`

//TODO:

Framework uses strings as locators (no need to use `By` class). It automatically recognizes only css and xpath locators
Each wrapper accepts a string or a list of strings as a locator parameter. In case of a list it appends list items
consequently to build complex locator.

Update the whole readme

Selenium webdriver: Modifying navigator.webdriver flag to prevent selenium detection
https://stackoverflow.com/questions/53039551/selenium-webdriver-modifying-navigator-webdriver-flag-to-prevent-selenium-detec/53040904#53040904

all dependencies should be the same as in framework

describe steps defined in the framework

describe how to extend basic classes

describe the possibility to use pure strings as css and xpath locators

Add links to all the lids used in framework

Add Jenkins pipelines to split tests, seed jobs etc.

Describe how to work with all classes in the framework

Add examples to all descriptions

add a full list of system props

add information on how to run tests in Jenkins in parallel

add an example build.gradle file to quickly setup new project
