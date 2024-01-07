package com.nr.nrsales.model

data class UserDashboardModel(
    val message: String,
    val result: List<Result>,
    val status: String
){
    data class Result(
        val total_deposit: String="0.0",
        val total_earns: String="0.0",
        val wallet_balance: String="0.0",
        val withdrawals: String="0.0",
        val first_name: String="",
        val notification_count: Int=0
    )
}
