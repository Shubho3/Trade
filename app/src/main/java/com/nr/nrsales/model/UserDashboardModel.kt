package com.nr.nrsales.model

data class UserDashboardModel(
    val message: String,
    val result: List<Result>,
    val status: String
){
    data class Result(
        val total_deposit: String,
        val total_earns: String,
        val wallet_balance: String,
        val withdrawals: String,
        val first_name: String,
        val notification_count: Int
    )
}

