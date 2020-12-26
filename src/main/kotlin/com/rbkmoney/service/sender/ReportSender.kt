package com.rbkmoney.service.sender

import com.rbkmoney.service.TransactionReport

interface ReportSender {
    fun sendReport(report: TransactionReport)
}