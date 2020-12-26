package com.rbkmoney.service.sender

import com.rbkmoney.service.TransactionReport
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private val log = getLogger(KafkaReportSender::class.java)

@Service
internal class KafkaReportSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${spring.kafka.topics.reports}")
    private val reportsTopic: String,
) : ReportSender {
    override fun sendReport(report: TransactionReport) {
        log.trace("Send {} report to kafka {} topic", report, reportsTopic)
        kafkaTemplate.send(reportsTopic, report.toString())
    }
}