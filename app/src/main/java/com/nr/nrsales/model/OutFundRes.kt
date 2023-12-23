package com.nr.nrsales.model

data class OutFundRes(
    val message: String, val result: ArrayList<Result>, val status: String
) {
    data class Result(
        val acount_pass: String, val amount: String, val date_time: String, val id: String
    )
}

data class OutFundResAdmin(
    val message: String,
    val result: List<Result>,
    val status: String
)
{
data class Result(
    val amount_id: String,
    val date_time: String,
    val id: String,
    val payment_receipt: String,
    val transation_id: String,
    val user_id: String
)}