package com.nr.nrsales.model

data class NotificationRes(
    val message: String, val result: ArrayList<Result>, val status: String
) {
    data class Result(
        val id: String,
        val user_name: String,
        val title_name: String,
        val description: String,
        val amount: String,
        val date_time: String,
        val status: String
    )
}
