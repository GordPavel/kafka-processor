package com.rbkmoney

import com.rbkmoney.data.TransactionEntity
import com.rbkmoney.data.TransactionRepository
import com.rbkmoney.service.TransactionRecord
import com.rbkmoney.service.TransactionReport
import com.rbkmoney.service.TransactionReportStatus
import com.rbkmoney.service.TransactionsProcessor
import com.rbkmoney.service.reports.SimpleReportGenerator
import com.rbkmoney.service.sender.KafkaReportSender
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.util.concurrent.ListenableFuture
import spock.lang.Specification
import spock.lang.Unroll

class TransactionProcessingTest extends Specification {

    TransactionRepository transactionRepository = Mock()
    KafkaTemplate<String, TransactionReport> kafkaTemplate = Mock()
    SimpleReportGenerator simpleReportGenerator = new SimpleReportGenerator()
    KafkaReportSender kafkaReportSender = new KafkaReportSender(kafkaTemplate, "results")
    TransactionsProcessor transactionsProcessor = new TransactionsProcessor(
            transactionRepository,
            ["simpleReportGenerator": simpleReportGenerator],
            ["kafkaReportSender": kafkaReportSender]
    )

    @Unroll
    def "Simple report generator and kafka sender test"() {
        given: "Returns entity by id"
        1 * transactionRepository.findById(_) >> transactionEntity

        and: "Handle report sending"
        def report = null
        1 * kafkaTemplate.send(_ as String, _) >> { arguments ->
            report = arguments[1] as TransactionReport
            return Mock(ListenableFuture)
        }

        when:
        transactionsProcessor.processTransaction(transactionRecord)

        then: "Correct report"
        report == expectedReport

        where:
        transactionRecord                | transactionEntity                              || expectedReport
        new TransactionRecord(1, 5.0, 0) | Optional.of(new TransactionEntity(1, 5.0, "")) || new TransactionReport(TransactionReportStatus.EQUAL, 1, 5.0, 5.0)
        new TransactionRecord(1, 6.0, 0) | Optional.of(new TransactionEntity(1, 5.0, "")) || new TransactionReport(TransactionReportStatus.NOT_EQUAL, 1, 6.0, 5.0)
        new TransactionRecord(1, 6.0, 0) | Optional.empty()                               || new TransactionReport(TransactionReportStatus.NOT_FOUND, 1, 6.0, null)
    }

}
