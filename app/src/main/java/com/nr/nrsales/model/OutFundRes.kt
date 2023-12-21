package com.nr.nrsales.model

data class OutFundRes(
    val message: String, val result: ArrayList<Result>, val status: String
) {
    data class Result(
        val acount_pass: String, val amount: String, val date_time: String, val id: String
    )
}