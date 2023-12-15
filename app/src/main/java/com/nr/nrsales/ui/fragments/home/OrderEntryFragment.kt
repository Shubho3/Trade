package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentOrderEnteryBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderEntryFragment :BaseFragment(R.layout.fragment_order_entery){
    lateinit var mBinding : FragmentOrderEnteryBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var jsonObject: JSONObject


    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentOrderEnteryBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Order Entry"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

        mBinding.ivEdit.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_orderEntryFragment_to_editProfileFragment)

        }

        mBinding.tvBack.setOnClickListener { onBackPressed() }

        mBinding.btnNext.setOnClickListener {
            if(mBinding.edtName.text.toString().equals("")) Toast.makeText(context,"Please enter name",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtContact.text.toString().equals("")) Toast.makeText(context,"Please enter contact",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtPhone.text.toString().equals("")) Toast.makeText(context,"Please enter Phone",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtEmail.text.toString().equals("")) Toast.makeText(context,"Please enter Email",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtAddressLineOne.text.toString().equals("")) Toast.makeText(context,"Please enter Address line1",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtAddressLineTwo.text.toString().equals("")) Toast.makeText(context,"Please enter Address line2",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtCity.text.toString().equals("")) Toast.makeText(context,"Please enter City",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtSate.text.toString().equals("")) Toast.makeText(context,"Please enter State",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtPostalCode.text.toString().equals("")) Toast.makeText(context,"Please enter Postal Code",
                Toast.LENGTH_LONG).show()
            else if(mBinding.edtCountry.text.toString().equals("")) Toast.makeText(context,"Please enter Country",
                Toast.LENGTH_LONG).show()
            else {


                val bundle = Bundle()
                bundle.putString("name", mBinding.edtName.text.toString())
                bundle.putString("contact", mBinding.edtContact.text.toString())
                bundle.putString("phone", mBinding.edtPhone.text.toString())
                bundle.putString("email", mBinding.edtEmail.text.toString())
                bundle.putString("address1", mBinding.edtAddressLineOne.text.toString())
                bundle.putString("address2", mBinding.edtAddressLineTwo.text.toString())
                bundle.putString("city", mBinding.edtCity.text.toString())
                bundle.putString("state", mBinding.edtSate.text.toString())
                bundle.putString("postalCode", mBinding.edtPostalCode.text.toString())
                bundle.putString("country", mBinding.edtCountry.text.toString())
                Navigation.findNavController(it).navigate(R.id.action_orderEntryFragment_to_deliveryLocationFragment,bundle)

            }
        }


    }


    override fun onResume() {
        super.onResume()
        getUserProfile()
    }

    private fun getUserProfile() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)

        val call = api.getUserProfile("867bb48f-ade8-4688-954b-12668ea07977",sharedPrf.getStoredTag(SharedPrf.USER_ID))
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
                       mBinding.tvName.text = jsonObject/*getJSONArray("Contacts").getJSONObject(0)*/.getString("Name")
                        mBinding.tvAddress.text = jsonObject.getString("AddressLine1")  + ", " + jsonObject.getString("AddressLine2") + ", " + jsonObject.getString("City") + ", " + jsonObject.getString("State") + ", " + jsonObject.getString("Country")


                        mBinding.tvAccount.text = ":  "+jsonObject.getString("AccountNumber")
                        mBinding.tvPhone.text = ":  "+jsonObject.getString("Phone")
                        mBinding.tvEmail.text = ":  "+jsonObject.getString("Email")
                        mBinding.tvRequestedBy.text =  ":  "+jsonObject.getString("PrimaryContactName")
                        (mBinding.edtName as TextView).text  =    jsonObject./*getJSONArray("Contacts").getJSONObject(0).*/getString("Name")
                        (mBinding.edtContact as TextView).text = jsonObject.getString("PrimaryContactName")
                        (mBinding.edtPhone as TextView).text = jsonObject.getString("Phone")
                        (mBinding.edtEmail as TextView).text = jsonObject.getString("Email")
                        (mBinding.edtAddressLineOne as TextView).text = jsonObject.getString("AddressLine1")
                        (mBinding.edtAddressLineTwo as TextView).text = jsonObject.getString("AddressLine2")
                        (mBinding.edtCity as TextView).text = jsonObject.getString("City")
                        (mBinding.edtSate as TextView).text = jsonObject.getString("State")
                        (mBinding.edtPostalCode as TextView).text = jsonObject.getString("PostalCode")
                        (mBinding.edtCountry as TextView).text = jsonObject.getString("Country")





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
        val TAG = OrderEntryFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): OrderEntryFragment {
            val fragment = OrderEntryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }







}
