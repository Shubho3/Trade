package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentInvoiceBinding
import com.nr.nrsales.model.InvoiceModel
import com.nr.nrsales.ui.adapter.InvoiceAdapter
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


class InvoiceFragment : BaseFragment(R.layout.fragment_invoice){
    lateinit var mBinding : FragmentInvoiceBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var arrayList : ArrayList<InvoiceModel>
    lateinit var invoiceAdapter : InvoiceAdapter

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentInvoiceBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Invoices"

        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        arrayList = ArrayList()

        invoiceAdapter = InvoiceAdapter(context,arrayList)
        mBinding.rvInvoice.adapter = invoiceAdapter

        getAllInvoiceIds()

    }




    private fun getAllInvoiceIds() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)

        val call = api.getAllInvoiceIds("867bb48f-ade8-4688-954b-12668ea07977","6d7671c7-90d5-493c-9ba6-b89ab002abb8")
       // val call = api.getAllInvoiceIds("867bb48f-ade8-4688-954b-12668ea07977",sharedPrf.getStoredTag(SharedPrf.USER_ID))
       // Log.e(OrderEntryFragment.TAG, "init: " + sharedPrf.getStoredTag(SharedPrf.USER_ID))

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    GlobalUtility.hideProgressMessage()
                    if(response.code()==200) {
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
                            getInvoiceById(json.get(i).toString())
                        }
                    }
                    else {
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

    private fun getInvoiceById(orderId: String) {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)

        val call = api.getInvoice("867bb48f-ade8-4688-954b-12668ea07977",orderId)
        Log.e(OrderEntryFragment.TAG, "init: " + orderId)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    GlobalUtility.hideProgressMessage()
                    val stringRes = response.body()!!.string()
                    Log.e("get invoice", "stringRes = ${stringRes}")
                     var jsonObject  = JSONObject(stringRes)

                     arrayList.add(InvoiceModel(jsonObject.getString("Customer"),
                         jsonObject.getString("ID"),
                         jsonObject.getString("InvoiceNumber"),
                         jsonObject.getString("Date"),
                                 jsonObject.getString("DueDate"),
                                 jsonObject.getBoolean("IsPaid"),
                                 jsonObject.getString("Memo"),
                                 jsonObject.getBoolean("TransferredToQuickBooksDesktop"),
                                 jsonObject.getDouble("TotalAmount")))
                    invoiceAdapter.notifyDataSetChanged()

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
        val TAG = InvoiceFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): InvoiceFragment {
            val fragment = InvoiceFragment()
            fragment.arguments = bundle
            return fragment
        }
    }







}
