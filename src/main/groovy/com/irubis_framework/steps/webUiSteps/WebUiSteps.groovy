/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.steps.webUiSteps

import com.irubis_framework.helpers.browser.Browser
import com.irubis_framework.steps.Steps
import org.openqa.selenium.remote.Augmenter
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies

import javax.imageio.ImageIO

/**
 * Created by Igor_Rubis. 7/29/16.
 */

abstract class WebUiSteps extends Steps {

    @Override
    protected eventually(interval = INTERVAL, closure) {
        long end = new Date().getTime() + interval
        Throwable exception = null

        while (new Date().getTime() <= end) {
            try {
                return closure()
            } catch (Throwable e) {
                exception = e
            }
        }
        takeScreenShot()
        throw exception
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private takeScreenShot() throws IOException {
        def augmentedDriver = new Augmenter().augment(Browser.getInstance())
        def image = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(augmentedDriver).getImage()
        def baos = new ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        baos.flush()
        byte[] imageInByte = baos.toByteArray()
        baos.close()
        return imageInByte
    }
}