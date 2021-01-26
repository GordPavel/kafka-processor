package com.rbkmoney.service.reports

import com.rbkmoney.data.TransactionEntity
import com.rbkmoney.service.TransactionRecord
import com.rbkmoney.service.TransactionReport
import com.rbkmoney.service.TransactionReportStatus
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

private val log = getLogger(SimpleReportGenerator::class.java)

@Service
internal class SimpleReportGenerator : ReportGenerator {

    override fun generateReport(
        transactionRecord: TransactionRecord,
        storedTransaction: TransactionEntity?
    ) = TransactionReport(
        storedTransaction?.amount?.let {
            if (it == transactionRecord.amount) TransactionReportStatus.EQUAL else TransactionReportStatus.NOT_EQUAL
        } ?: TransactionReportStatus.NOT_FOUND,
        transactionRecord.id,
        transactionRecord.amount,
        storedTransaction?.amount
    ).also {
        log.trace("Generated report {} for {} transaction", it, transactionRecord)
    }

}