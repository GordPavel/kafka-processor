package com.rbkmoney.listener

import com.rbkmoney.service.TransactionRecord
import com.rbkmoney.service.TransactionsProcessor
import org.slf4j.LoggerFactory.getLogger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = getLogger(TransactionsListener::class.java)

@Component
class TransactionsListener(
    private val transactionsProcessor: TransactionsProcessor,
) {
    @KafkaListener(topics = ["\${spring.kafka.topics.transactions}"])
    fun handle(transaction: TransactionRecord, acknowledgment: Acknowledgment) {
        log.trace("Handle transaction {}", transaction)
        transactionsProcessor.processTransaction(transaction)
        log.trace("Transaction {} successfully proceeded", transaction)
        acknowledgment.acknowledge()
    }
}