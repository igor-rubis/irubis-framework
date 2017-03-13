/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework

import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError
import ru.yandex.qatools.allure.junit.AllureRunListener

class AllureTestRunner extends BlockJUnit4ClassRunner {

    AllureTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    void run(RunNotifier notifier) {
        // JUnit don't support Java SPI for adding test listeners.
        // To add global Listener we should manually create JUnitCore.
        // With gradle we don't have (?) such option.
        // Solution - add listener via runner.
        // And manually call "runStarted" and "runFinished" events.
        notifier.addListener(new AllureRunListener());

        // Allure do nothing on testRunStarted
        // Just for consitency.
        notifier.fireTestRunStarted(Description.createSuiteDescription("Tests"));

        super.run(notifier);

        // Allure don't use Result from JUnit.
        // It gathers results on its own.
        // So, basically any param is OK here.
        notifier.fireTestRunFinished(new Result());
    }
}
