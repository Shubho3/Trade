package com.nr.nrsales.model

data class BasicRes(
    val message: String,
    val result: String,
    val status: String
)
data class UserListRes(
    val message: String,
    val result: ArrayList<User>,
    val status: String
)
data class UserRes(
    val message: String,
    val result: User,
    val status: String
)

data class User(
    val aadhar_back: String,
    val aadhar_front: String,
    val address: String,
    val amount: String,
    val date_time: String,
    val description: String,
    val email: String,
    val first_name: String,
    val gender: String,
    val id: String,
    val image: String,
    val ios_register_id: String,
    val last_name: String,
    val lat: String,
    val lon: String,
    val mobile: String,
    val otp: String,
    val pan_back: String,
    val pan_front: String,
    val password: String,
    val register_id: String,
    val services_id: String,
    val services_name: String,
    val social_id: String,
    val status: String,
    val total_deposit: String,
    val total_earns: String,
    val type: String,
    val user_name: String,
    val wallet_balance: String,
    val withdrawals: String
)


    
