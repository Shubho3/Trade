package com.nr.nrsales.apis

import com.nr.nrsales.model.CsvResModel
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.model.UserRes
import com.nr.nrsales.utils.Constants
import com.nr.nrsales.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url
import java.io.File
import java.util.Objects

interface ApiService {

    @GET(Constants.LOGIN_URL)
    suspend fun loginUser(@QueryMap map: HashMap<String, Any>): Response<UserRes>

   @POST(Constants.REGISTER_URL)
    suspend fun registerUser(@Body requestBody: RequestBody):
            Response<RegisterResModel>
    @POST(Constants.update_profile)
    suspend fun updateUser(@Body requestBody: RequestBody):
            Response<RegisterResModel>
   @GET(Constants.get_profile)
    suspend fun get_profile(@QueryMap map: HashMap<String, Any>): Response<RegisterResModel>

/*    @GET(Constants.CSV_URL)
    suspend fun getCsvList(): Response<CsvResModel>*/
@GET("api/order/report/")
fun downloadFile(@Header("Authorization") auth:
                 String,@QueryMap map: HashMap<String, Any>):
        Response<File>

}
