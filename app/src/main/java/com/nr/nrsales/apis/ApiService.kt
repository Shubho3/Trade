package com.nr.nrsales.apis

import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.BasicRes
import com.nr.nrsales.model.NotificationRes
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.OutFundResAdmin
import com.nr.nrsales.model.PositionRes
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.model.UserDashboardModel
import com.nr.nrsales.model.UserListRes
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

    @GET(Constants.get_all_user)
    suspend fun get_all_user(@QueryMap map: HashMap<String, Any>): Response<UserListRes>

    @GET(Constants.get_add_funds_list_admin)
    suspend fun get_add_funds_list_admin(@QueryMap map: HashMap<String, Any>): Response<AddFundRes>

    @GET(Constants.get_out_funds_list_admin)
    suspend fun get_out_funds_list_admin(@QueryMap map: HashMap<String, Any>): Response<OutFundResAdmin>

    @GET(Constants.add_fund_accept_reject)
    suspend fun add_fund_accept_reject(@QueryMap map: HashMap<String, Any>): Response<BasicRes>

    @GET(Constants.out_funt_accept_reject)
    suspend fun out_funt_accept_reject(@QueryMap map: HashMap<String, Any>): Response<BasicRes>

    @GET(Constants.position)
    suspend fun position(@QueryMap map: HashMap<String, Any>): Response<BasicRes>

    @GET(Constants.position_list)
    suspend fun position_list(@QueryMap map: HashMap<String, Any>): Response<PositionRes>

    @GET(Constants.get_notification)
    suspend fun get_notification(@QueryMap map: HashMap<String, Any>): Response<NotificationRes>
 @GET(Constants.update_user_status)
    suspend fun update_user_status(@QueryMap map: HashMap<String, Any>): Response<BasicRes>

    @GET("api/order/report/")
    fun downloadFile(
        @Header("Authorization") auth: String, @QueryMap map: HashMap<String, Any>
    ): Response<File>

}
