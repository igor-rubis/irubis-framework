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
or

##### Allure 2
```https://docs.qameta.io/allure/#_installing_a_commandline```
