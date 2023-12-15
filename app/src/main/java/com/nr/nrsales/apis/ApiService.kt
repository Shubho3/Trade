package com.nr.nrsales.apis

import com.nr.nrsales.model.CsvResModel
import com.nr.nrsales.model.RegisterResModel
import com.nr.nrsales.utils.Constants
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
    suspend fun loginUser(@Header("Authorization") auth: String, @QueryMap map: HashMap<String, Any>): Response<String>

    @POST(Constants.REGISTER_URL)
    suspend fun registerUser(@Header("Authorization") auth: String, @Body map: HashMap<String, Any>): Response<RegisterResModel>

   @GET("https://technorizen.com/_API/get_csv.php")
    suspend fun getCsvList(@Url auth: String): Response<CsvResModel>

/*    @GET(Constants.CSV_URL)
    suspend fun getCsvList(): Response<CsvResModel>*/
@GET("api/order/report/")
fun downloadFile(@Header("Authorization") auth:
                 String,@QueryMap map: HashMap<String, Any>):
        Response<File>

}
