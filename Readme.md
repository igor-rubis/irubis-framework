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
> To init tag in the repo go to https://jitpack.io/com/github/igor-rubis/irubis-framework/<tag>

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
##### Allure 1
```bash
sudo apt-add-repository ppa:yandex-qatools/allure-framework
sudo apt-get update
sudo apt-get install allure-commandline
```

##### Allure 2
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