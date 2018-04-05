# My self-educational project to create automation testing framework from scratch.

## Add to your project as a dependency to the build.gradle file
```
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.igor-rubis:irubis-framework:master'
}
```

## Command line options:
* testsMode
    * mobile
    * remote
    * local
* browser
    * firefox
    * chrome
    * jBrowser
    * electron
* allure.results.directory (`build/reports/allure`)
* maxParallelForks
* apiProxy
* electronBinary
* pollingInterval
* url
* uiHubUrl
* mobileHubUrl
* testsFolder

## TODO:
* bdd
* API tests
* Implement test-splitter to paralellize test run by batches
* Consider adding feature to compare screenshots for testing the front end

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
```
//def allureExecutable = 'D:\\Downloads\\allure-2.5.0\\bin\\allure.bat'
def allureExecutable = '/usr/bin/allure'

task openAllureReport(type: Exec) {
    executable allureExecutable
//    args('open') // for allure 2
    args('report', 'open')
}

task generateAllureReport(type: Exec) {
    executable allureExecutable
//    args('generate', '--clean', 'build/reports/allure') // for allure 2, which requires '--clean' parameter
    args('generate', 'build/reports/allure')
}
```