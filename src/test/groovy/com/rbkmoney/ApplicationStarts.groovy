package com.rbkmoney

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationStarts extends Specification {
    @Autowired
    ApplicationContext context

    def "Check context successfully starts"() {
        expect:
        context != null
    }
}
