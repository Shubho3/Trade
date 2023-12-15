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
    lateinit var modelLogin: CsvResModel2
    private lateinit var arrayList: ArrayList<CsvResModel2.Datas>
    var strImport  ="0"
    var strCustomsDuty  ="0"
    var strVatTax  ="0"


    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentGetAQuoteBinding
        context = requireActivity()
        init()
      //  getAllTypeData()

      //  viewmodel.fetchAllTypeResponse()
        getAllTypeData()
    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Quote"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
       arrayList = ArrayList<CsvResModel2.Datas>()

        mBinding.autoSearch.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            if(!arrayList.get(i).IMPORT_DUTY.equals("")) {
                strImport = arrayList.get(i).IMPORT_DUTY.toString()
                mBinding.edtServiceChargeRate.text =  strCustomsDuty +"%"

            }
            if(!arrayList.get(i).CSC.equals("")) {
                strCustomsDuty = arrayList.get(i).CSC.toString()
                mBinding.edtCustomsDuteRate.text =  strImport +"%"

            }
            if(!arrayList.get(i).VAT.equals("")) {
                strVatTax = arrayList.get(i).VAT.toString()
                mBinding.edtValueTaxRate.text = strVatTax +"%"
            }


        })

        mBinding.btnSubmit.setOnClickListener{
            validation()
        }


        mBinding.btnReset.setOnClickListener{
            clearValues()
        }

    }

    private fun clearValues() {
        mBinding.autoSearch.text.clear()
        mBinding.edtServiceChargeRate.text ="";
        mBinding.edtCustomsDuteRate.text =""
        mBinding.edtValueTaxRate.text =""

        mBinding.edtCustomsDuty.text =""
        mBinding.edtServiceCharge.text = ""
        mBinding.edtValueTax.text = ""
        mBinding.edtShippingCharge.text = ""
        mBinding.edtInsurance.text =""

        mBinding.edtTotalCustoms.text = ""
        mBinding.edtTotalNr.text = ""
        mBinding.edtTotalEstimate.text =""

        mBinding.edtValue.text .clear()
        mBinding.edtWeight.text.clear()

    }

    private fun validation() {
        if(mBinding.edtValue.text.toString().equals("")) Toast.makeText(activity,"Please enter value",Toast.LENGTH_LONG).show()
        else if(mBinding.edtWeight.text.toString().equals("")) Toast.makeText(activity,"Please enter Weight",Toast.LENGTH_LONG).show()
        else calculationValue()
    }

    private fun calculationValue(){
        var doubleImport  = strImport.toDouble()
        var doubleCustomsDuty  = strCustomsDuty.toDouble()
        var doubleVatTax  = strVatTax.toDouble()
        val txtValue = mBinding.edtValue.text.toString().toDouble() * 2.70
        val txtWeight = mBinding.edtWeight.text.toString().toDouble()

        val totalCustomDuty = (txtValue * doubleImport) / 100
        val totalService = (txtValue * doubleCustomsDuty) / 100


        val totalVatD = (txtValue + totalCustomDuty + totalService)

        val totalVat =  (totalVatD * doubleVatTax) / 100

        val totalShippingCharge = txtWeight * 9.0


        mBinding.edtCustomsDuty.text = String.format( "%.2f", totalCustomDuty)
        mBinding.edtServiceCharge.text = String.format( "%.2f", totalService)
        mBinding.edtValueTax.text = String.format("%.2f", totalVat)
        mBinding.edtShippingCharge.text = String.format("%.2f", totalShippingCharge)




        val totalcistom = (totalCustomDuty + totalService + totalVat)

        var Insurance = 0.0

        Insurance = (txtValue/270) * 4


        if (Insurance == 0.0) {
            Insurance = 4.0
            mBinding.edtInsurance.text = String.format("%.2f", Insurance)

        } else {
            mBinding.edtInsurance.text = String.format("%.2f", Insurance)
        }

        if (Insurance > 32) {
            mBinding.edtInsurance.text = "Call for rate"
            Insurance = 0.0
        }

        val totalNR = (totalShippingCharge + Insurance)


       // print("sdsdsd\(Double(textValu/270).whole)")

        mBinding.edtTotalCustoms.text = String.format("%.2f", totalcistom)
        mBinding.edtTotalNr.text = String.format("%.2f", totalNR)

        val totaEstai = (totalcistom + totalNR)

        mBinding.edtTotalEstimate.text = String.format("%.2f", totaEstai)

    }








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






   /*  fun getAllTypeData(){
        viewmodel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        Log.e(GetAQuoteFragment.TAG, "init: " + it.toString())
                    }
                }
                is NetworkResult.Error -> {
                    GlobalUtility.hideProgressMessage()
                    Log.e(LoginFragment.TAG, "fetchAllTypeDataResponse: " + response.message)
                    Toast.makeText(
                        context,
                        "data Not Found",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is NetworkResult.Loading -> {
                    GlobalUtility.hideProgressMessage()
                }
            }
        }
    }*/


    companion object {
        val TAG = GetAQuoteFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): GetAQuoteFragment {
            val fragment = GetAQuoteFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
