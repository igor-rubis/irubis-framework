/**
 * Copyright 2016 Igor Rubis
 * Licensed under the Apache License, Version 2.0
 */

package com.irubis_framework.pages.homePage

import com.irubis_framework.pages.Page
import org.openqa.selenium.By

/**
 * Created by Igor_Rubis. 7/29/16.
 */
class HomePage extends Page {
    String url() {
        'https://www.google.com/'
    }

    def title = By.tagName('title')
}