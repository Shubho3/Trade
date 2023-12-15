package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentInvoiceBinding
import com.nr.nrsales.model.InvoiceModel
import com.nr.nrsales.model.OrderModel
import com.nr.nrsales.ui.adapter.InvoiceAdapter
import com.nr.nrsales.ui.adapter.OrderAdapter
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListFragment : BaseFragment(R.layout.fragment_invoice) {
    lateinit var mBinding: FragmentInvoiceBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var arrayList: ArrayList<OrderModel>
    lateinit var orderAdapter: OrderAdapter
    private lateinit var jsonObject: JSONObject


    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentInvoiceBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Tracking"

        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        arrayList = ArrayList()

        orderAdapter = OrderAdapter(context, arrayList)
        mBinding.rvInvoice.adapter = orderAdapter

        getUserProfile()
       // getAllOrderIds("")


    }

    private fun getUserProfile() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)

        val call = api.getUserProfile("867bb48f-ade8-4688-954b-12668ea07977",sharedPrf.getStoredTag(
            SharedPrf.USER_ID))
        Log.e(OrderEntryFragment.TAG, "init: " + sharedPrf.getStoredTag(SharedPrf.USER_ID))

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    GlobalUtility.hideProgressMessage()
                    val stringRes = response.body()!!.string()
                    Log.e("jafhkjdf", "stringRes = ${stringRes}")
                    jsonObject = JSONObject(stringRes)
                    //  if (jsonObject.getString("status").equals("1")) {
                    //  modelLogin = Gson().fromJson(stringRes, CsvResModel2::class.java)
                    // Log.e("jafhkjdf", "stringRes = ${modelLogin.getResult().toString()}")
                    getAllOrderIds(jsonObject.getString("Name"))






                    //  } else {
                    // Toast.makeText(mContext, getString(R.string.unsuccessful), Toast.LENGTH_SHORT).show();
                    //  }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    GlobalUtility.hideProgressMessage()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                GlobalUtility.hideProgressMessage()
            }
        })
    }




    private fun getAllOrderIds(name : String) {
        GlobalUtility.showProgressMessage(
            requireActivity(),
            requireActivity().getString(R.string.loading)
        )
        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)
        val call = api.getAllOrderIds("867bb48f-ade8-4688-954b-12668ea07977", name)

      //  val call = api.getAllOrderIds("867bb48f-ade8-4688-954b-12668ea07977", "Technorizen")
        // Log.e(OrderEntryFragment.TAG, "init: " + sharedPrf.getStoredTag(SharedPrf.USER_ID))

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    GlobalUtility.hideProgressMessage()
                    if (response.code() == 200) {
                        val stringRes = response.body()!!.string()
                        Log.e("get order ids", "stringRes = ${stringRes}")
                        /// var jsonObject  = JSONArray(stringRes)
                        //  if (jsonObject.getString("status").equals("1")) {
                        //  modelLogin = Gson().fromJson(stringRes, CsvResModel2::class.java)
                        // Log.e("jafhkjdf", "stringRes = ${modelLogin.getResult().toString()}")
                        val json = JSONArray(stringRes)
                        Log.e("jafhkjdf", json.length().toString())
                        for (i in 0..json.length() - 1) {
                            Log.e("value====", json.get(i).toString())
                            getOrderById(json.get(i).toString())
                        }
                    } else {
                        Toast.makeText(context, "not fount", Toast.LENGTH_SHORT).show();
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    GlobalUtility.hideProgressMessage()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                GlobalUtility.hideProgressMessage()
            }
        })
    }

    private fun getOrderById(orderId: String) {
        GlobalUtility.showProgressMessage(
            requireActivity(),
            requireActivity().getString(R.string.loading)
        )
        val api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)

        val call = api.getOrder("867bb48f-ade8-4688-954b-12668ea07977", orderId)
        Log.e(OrderEntryFragment.TAG, "init: " + orderId)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    GlobalUtility.hideProgressMessage()
                    val stringRes = response.body()!!.string()
                    Log.e("get order", "stringRes = ${stringRes}")
                    var jsonObject = JSONObject(stringRes)

                    arrayList.add(
                        OrderModel(
                            jsonObject.getString("ID"),
                            jsonObject.getString("TrackingNumber"),
                            jsonObject.getJSONObject("Status").getInt("Level").toString(),
                            jsonObject.getString("DateSubmitted"),
                            jsonObject.getString("RequestedBy"),
                            jsonObject.getDouble("TotalCost"),
                            jsonObject.getString("Description"),
                            jsonObject.getDouble("Weight"),
                            jsonObject.getJSONObject("CollectionLocation").getString("AddressLine1")
                                    + " " + jsonObject.getJSONObject("CollectionLocation")
                                .getString("AddressLine2")
                                    + " " + jsonObject.getJSONObject("CollectionLocation")
                                .getString("City")
                                    + " " + jsonObject.getJSONObject("CollectionLocation")
                                .getString("State")
                                    + " " + jsonObject.getJSONObject("CollectionLocation")
                                .getString("PostalCode"),

                            jsonObject.getJSONObject("DeliveryLocation").getString("AddressLine1")
                                    + " " + jsonObject.getJSONObject("DeliveryLocation")
                                .getString("AddressLine2")
                                    + " " + jsonObject.getJSONObject("DeliveryLocation")
                                .getString("City")
                                    + " " + jsonObject.getJSONObject("DeliveryLocation")
                                .getString("State")
                                    + " " + jsonObject.getJSONObject("DeliveryLocation")
                                .getString("PostalCode"),
                            jsonObject.getDouble("Distance")))

                    orderAdapter.notifyDataSetChanged()

                    //  } else {
                    // Toast.makeText(mContext, getString(R.string.unsuccessful), Toast.LENGTH_SHORT).show();
                    //  }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    GlobalUtility.hideProgressMessage()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                GlobalUtility.hideProgressMessage()
            }
        })
    }


    companion object {
        val TAG = OrderListFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): OrderListFragment {
            val fragment = OrderListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}