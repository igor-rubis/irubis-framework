package com.irubis_framework.helpers.scripts

import com.irubis_framework.helpers.jvmProperties.JVMProperties
import groovy.io.FileType

class SplitTests {
    static void main(agrs) {
        def testsFolder = JVMProperties.CURRENT_FOLDER + JVMProperties.TESTS_FOLDER
        def files = []
        def folder = new File(testsFolder)

        folder.eachFileRecurse(FileType.FILES) { file ->
            if (file.name.endsWith('.groovy') && file.text.contains('@Test')) {
                files << file.name.replace('.groovy', '')
            }
        }

        new File('tests_list.csv').write(files.join(','))
    }
}
