package com.nr.nrsales.model

data class AddFundRes(
    val message: String,
    val result: ArrayList<Result>,
    val status: String
){
    data class Result(
        val amount_id: String,
        val id: String,
        val payment_receipt: String,
        val transation_id: String,
        val status: String

    )
}
