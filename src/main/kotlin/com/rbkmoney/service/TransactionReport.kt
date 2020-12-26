package com.rbkmoney.service

data class TransactionReport(
    val transactionReportStatus: TransactionReportStatus,
    val transactionId: Long,
    val receivedAmount: Double,
    val storedAmount: Double?,
)

enum class TransactionReportStatus {
    EQUAL,
    NOT_EQUAL,
    NOT_FOUND,
}