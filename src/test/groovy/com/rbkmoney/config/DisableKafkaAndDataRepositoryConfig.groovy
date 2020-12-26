package com.rbkmoney.config

import com.rbkmoney.data.TransactionRepository
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import spock.lang.Specification

@TestConfiguration
@EnableAutoConfiguration(
        exclude = [
                DataSourceAutoConfiguration,
                KafkaAutoConfiguration,
        ]
)
class DisableKafkaAndDataRepositoryConfig extends Specification {

    @Bean
    TransactionRepository transactionRepository() {
        Mock(TransactionRepository)
    }

    @Bean
    KafkaTemplate<?, ?> kafkaTemplate() {
        Mock(KafkaTemplate)
    }

}
