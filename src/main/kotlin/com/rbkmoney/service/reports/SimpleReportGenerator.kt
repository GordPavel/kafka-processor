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
    ): TransactionReport {
        val storedAmount: Double? = storedTransaction?.amount
        val reportStatus = if (storedAmount != null) {
            if (storedAmount == transactionRecord.amount) TransactionReportStatus.EQUAL else TransactionReportStatus.NOT_EQUAL
        } else TransactionReportStatus.NOT_FOUND
        val report = TransactionReport(
            reportStatus,
            transactionRecord.id,
            transactionRecord.amount,
            storedAmount
        )
        log.trace("Generated report {} for {} transaction", report, transactionRecord)
        return report
    }

}