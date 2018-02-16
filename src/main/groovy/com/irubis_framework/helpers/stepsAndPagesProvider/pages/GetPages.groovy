package com.irubis_framework.helpers.stepsAndPagesProvider.pages

import com.irubis_framework.pages.homePage.HomePage

class GetPages {
    private static homePage
    static HomePage getHomePage() {
        homePage = homePage ?: new HomePage()
    }
}
