package com.nr.nrsales.apis

import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(params: HashMap<String, Any>) = apiService.loginUser(params)
    suspend fun registerUser(body: RequestBody) = apiService.registerUser(body)

    suspend fun updateUser(body: RequestBody) = apiService.updateUser(body)

    suspend fun addFunds(body: RequestBody) = apiService.addFunds(body)
    suspend fun outFunds(body: RequestBody) = apiService.outFunds(body)

    suspend fun get_profile(Map: HashMap<String, Any>) = apiService.getProfile(Map)
    suspend fun user_dashboard(Map: HashMap<String, Any>) = apiService.user_dashboard(Map)
    suspend fun getFund(Map: HashMap<String, Any>) = apiService.getFund(Map)
    suspend fun out_funds_list(Map: HashMap<String, Any>) = apiService.out_funds_list(Map)

    suspend fun downloadPDF(params: HashMap<String, Any>) = apiService.downloadFile("867bb48f-ade8-4688-954b-12668ea07977", params)


}