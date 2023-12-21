package com.nr.nrsales.apis

import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.model.UserDashboardModel
import com.nr.nrsales.model.UserRes
import com.nr.nrsales.utils.Constants
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap
import java.io.File

interface ApiService {

    @GET(Constants.LOGIN_URL)
    suspend fun loginUser(@QueryMap map: HashMap<String, Any>): Response<UserRes>

    @POST(Constants.REGISTER_URL)
    suspend fun registerUser(@Body requestBody: RequestBody): Response<RegisterResModel>

    @POST(Constants.update_profile)
    suspend fun updateUser(@Body requestBody: RequestBody): Response<RegisterResModel>

    @POST(Constants.add_funds)
    suspend fun addFunds(@Body requestBody: RequestBody): Response<BasicRes>

    @POST(Constants.out_funds)
    suspend fun outFunds(@Body requestBody: RequestBody): Response<BasicRes>

    @GET(Constants.get_profile)
    suspend fun getProfile(@QueryMap map: HashMap<String, Any>): Response<RegisterResModel>

    @GET(Constants.add_funds_list)
    suspend fun getFund(@QueryMap map: HashMap<String, Any>): Response<AddFundRes>

    @GET(Constants.out_funds_list)
    suspend fun out_funds_list(@QueryMap map: HashMap<String, Any>): Response<OutFundRes>
    @GET(Constants.user_dashboard)
    suspend fun user_dashboard(@QueryMap map: HashMap<String, Any>): Response<UserDashboardModel>

    @GET("api/order/report/")
    fun downloadFile(
        @Header("Authorization") auth: String, @QueryMap map: HashMap<String, Any>
    ): Response<File>

}
