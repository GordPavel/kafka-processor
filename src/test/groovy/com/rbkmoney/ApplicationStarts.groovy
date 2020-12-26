package com.rbkmoney

import com.rbkmoney.config.DisableKafkaAndDataRepositoryConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import spock.lang.Specification

@SpringBootTest
@Import([
        DisableKafkaAndDataRepositoryConfig,
])
class ApplicationStarts extends Specification {
    @Autowired
    ApplicationContext context

    def "Check context successfully starts"() {
        expect:
        context != null
    }
}
