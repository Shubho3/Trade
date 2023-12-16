package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.nr.nrsales.R
import com.nr.nrsales.adapter.AutoCompleteAdapter
import com.nr.nrsales.databinding.FragmentGetAQuoteBinding
import com.nr.nrsales.model.CsvResModel2
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.viewmodel.home.GetAQuoteViewModel
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetAQuoteFragment : BaseFragment(R.layout.fragment_get_a_quote) {
    lateinit var mBinding : FragmentGetAQuoteBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<GetAQuoteViewModel>()
    private lateinit var arrayList: ArrayList<CsvResModel2.Datas>
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentGetAQuoteBinding
        context = requireActivity()
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Add Funds"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
       arrayList = ArrayList<CsvResModel2.Datas>()


        mBinding.btnSubmit.setOnClickListener{
            validation()
        }


    }


    private fun validation() {
        if(mBinding.edtValue.text.toString().equals("")) Toast.makeText(activity,"Please enter value",Toast.LENGTH_LONG).show()
        else return
    }

/*
    private fun getAllTypeData() {
        var api = activity?.let { ApiClient.getClient(it) }!!.create(NRApiService::class.java)
        val call = api.getCsvList()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val stringRes = response.body()!!.string()
                    val jsonObject = JSONObject(stringRes)
                    if (jsonObject.getString("status").equals("1")) {
                        modelLogin = Gson().fromJson(stringRes, CsvResModel2::class.java)
                        Log.e("jafhkjdf", "stringRes = ${modelLogin.getResult().toString()}")
                        arrayList.clear()
                        arrayList.addAll(modelLogin.getResult()!!)
                        val adapter = AutoCompleteAdapter(
                            activity!!,
                            R.layout.item_auto, //Your layout. Make sure it has [TextView] with id "tvStreetTitle"
                            arrayList //Your list goes here
                        )
                        mBinding.autoSearch.threshold = 2 //will start working from first character
                        mBinding.autoSearch.setAdapter(adapter)


                        // Toast.makeText(mContext, getString(R.string.successful), Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(mContext, getString(R.string.unsuccessful), Toast.LENGTH_SHORT).show();
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               // ProjectUtil.pauseProgressDialog()
            }
        })
    }
*/

    companion object {
        val TAG = GetAQuoteFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): GetAQuoteFragment {
            val fragment = GetAQuoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
