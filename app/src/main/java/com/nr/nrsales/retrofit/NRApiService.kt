package com.yayatotaxi.retrofit

import com.nr.nrsales.utils.Constants
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface NRApiService {



    @GET(Constants.GET_CUSTOMER_PROFILE_URL+"{id}")
    fun getUserProfile(@Header("Authorization") auth: String,@Path("id") appId: String): Call<ResponseBody>

    @POST(Constants.UPDATE_CUSTOMER_PROFILE_URL)
    fun updateCustomerProfile(@Header("Authorization") auth: String, @Body params: RequestBody?): Call<ResponseBody>




    @GET(Constants.GET_INVOICES_LIST_URL+"{id}")
    fun getInvoice(@Header("Authorization") auth: String,@Path("id") appId: String): Call<ResponseBody>

    @GET(Constants.GET_INVOICE_ORDER_URL)
    fun getAllInvoiceIds(@Header("Authorization") auth: String, @Query("customer") id: String): Call<ResponseBody>


    @GET(Constants.GET_ORDER_LIST_URL)
    fun getAllOrderIds(@Header("Authorization") auth: String, @Query("CustomerName") customerName: String): Call<ResponseBody>

    @GET(Constants.GET_ORDER_URL+"{id}")
    fun getOrder(@Header("Authorization") auth: String,@Path("id") appId: String): Call<ResponseBody>


    @POST("api/order/report/")
     fun downloadFile(@Header("Authorization") auth:
                      String,@Url fileUrl:String):
            Response<ResponseBody>

}