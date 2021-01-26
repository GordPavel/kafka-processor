package com.rbkmoney.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.rbkmoney.data.TransactionEntity
import com.rbkmoney.data.TransactionRepository
import com.rbkmoney.service.reports.ReportGenerator
import com.rbkmoney.service.sender.ReportSender
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

private val log = getLogger(TransactionsProcessor::class.java)

@Service
class TransactionsProcessor(
    private val transactionRepository: TransactionRepository,
    private val reportsGenerators: Map<String, ReportGenerator>,
    private val reportsSenders: Map<String, ReportSender>,
) {

    fun processTransaction(transaction: TransactionRecord) {
        val storedTransaction: TransactionEntity? = transactionRepository.findById(transaction.id).orElse(null)
        val reportGenerator = resolveReportTypeBy(transaction)
            ?: throw IllegalArgumentException("Can't resolve report type by transaction $transaction")
        val report = reportGenerator.generateReport(transaction, storedTransaction)
        val reportSender = resolveSenderTypeBy(transaction)
            ?: throw IllegalArgumentException("Can't resolve report sender by transaction $transaction")
        reportSender.sendReport(report)
    }

    //    simplest report type resolver
    private fun resolveReportTypeBy(transaction: TransactionRecord): ReportGenerator? =
        reportsGenerators["simpleReportGenerator"]
            .also {
                log.debug("Have chosen {} report generator for {} transaction", it, transaction)
            }

    //    simplest report type resolver
    private fun resolveSenderTypeBy(transaction: TransactionRecord): ReportSender? =
        reportsSenders["kafkaReportSender"].also {
            log.debug("Have chosen {} report sender for {} transaction", it, transaction)
        }

}

data class TransactionRecord(
    @JsonProperty("PID")
    val id: Long,
    @JsonProperty("PAMOUNT")
    val amount: Double,
    @JsonProperty("PDATA")
    val data: Long
)