package com.nr.nrsales.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("user_id")
    val userId: String
)


