package com.nr.nrsales.ui.fragments.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentDeliveryLocationBinding
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
import java.util.Calendar


class DeliveryLocationFragment : BaseFragment(R.layout.fragment_delivery_location){
    lateinit var mBinding : FragmentDeliveryLocationBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var jsonObject: JSONObject
    val map: HashMap<String, Any> = HashMap()
    lateinit var name: String
    lateinit var contact: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var address1: String
    lateinit var address2: String
    lateinit var city: String
    lateinit var state: String
    lateinit var postalCode: String
    lateinit var country: String



    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentDeliveryLocationBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        if(arguments!=null){
            name  = requireArguments().getString("name").toString()
            contact  = requireArguments().getString("contact").toString()
            phone  = requireArguments().getString("phone").toString()
            email  = requireArguments().getString("email").toString()
            address1  = requireArguments().getString("address1").toString()
            address2  = requireArguments().getString("address2").toString()
            city  = requireArguments().getString("city").toString()
            state  = requireArguments().getString("state").toString()
            postalCode  = requireArguments().getString("postalCode").toString()
            country  = requireArguments().getString("country").toString()
            Log.e("check value====", "stringRes = ${contact}")

        }

        mBinding.headerLay.tvLogo.text = "Order Entry"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }



        mBinding.tvBack.setOnClickListener { onBackPressed() }

        mBinding.btnNext.setOnClickListener {
            if(mBinding.edtContact.text.toString().equals("")) Toast.makeText(context,"Please enter contact name",
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
                bundle.putString("name", name)
                bundle.putString("contact", contact)
                bundle.putString("phone", phone)
                bundle.putString("email", email)
                bundle.putString("address1", address1)
                bundle.putString("address2", address2)
                bundle.putString("city", city)
                bundle.putString("state", state)
                bundle.putString("postalCode", postalCode)
                bundle.putString("country", country)
                bundle.putString("Dcontact", mBinding.edtContact.text.toString())
                bundle.putString("Dphone", mBinding.edtPhone.text.toString())
                bundle.putString("Demail", mBinding.edtEmail.text.toString())
                bundle.putString("Daddress1", mBinding.edtAddressLineOne.text.toString())
                bundle.putString("Daddress2", mBinding.edtAddressLineTwo.text.toString())
                bundle.putString("Dcity", mBinding.edtCity.text.toString())
                bundle.putString("Dstate", mBinding.edtSate.text.toString())
                bundle.putString("DpostalCode", mBinding.edtPostalCode.text.toString())
                bundle.putString("Dcountry", mBinding.edtCountry.text.toString())
                Navigation.findNavController(it).navigate(R.id.action_deliveryLocationFragment_to_orderEntryLastFragment,bundle)

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
                    mBinding.tvName.text = jsonObject.getString("Name")
                    mBinding.tvAddress.text = jsonObject.getString("AddressLine1")  + ", " + jsonObject.getString("AddressLine2") + ", " + jsonObject.getString("City") + ", " + jsonObject.getString("State") + ", " + jsonObject.getString("Country")


                    mBinding.tvAccount.text = ":  "+jsonObject.getString("AccountNumber")
                    mBinding.tvPhone.text = ":  "+jsonObject.getString("Phone")
                    mBinding.tvEmail.text = ":  "+jsonObject.getString("Email")
                    mBinding.tvRequestedBy.text =  ":  "+jsonObject.getString("PrimaryContactName")
                  //  (mBinding.edtContact as TextView).text = jsonObject.getString("PrimaryContactName")
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