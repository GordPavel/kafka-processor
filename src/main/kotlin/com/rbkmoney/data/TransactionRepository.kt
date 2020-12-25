package com.rbkmoney.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import javax.persistence.*

@Component
interface TransactionRepository : CrudRepository<TransactionEntity, Long>

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1,
    @Column
    var amount: Double,
    @Column
    var data: String
) {
    constructor() : this(-1, 0.0, "")
}