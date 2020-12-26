package com.rbkmoney.service.reports

import com.rbkmoney.data.TransactionEntity
import com.rbkmoney.service.TransactionRecord
import com.rbkmoney.service.TransactionReport

interface ReportGenerator {
    fun generateReport(
        transactionRecord: TransactionRecord,
        storedTransaction: TransactionEntity?
    ): TransactionReport
}
