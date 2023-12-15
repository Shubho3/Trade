package com.nr.nrsales.ui.fragments.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentDeliveryLocationBinding
import com.nr.nrsales.databinding.FragmentLastOrderEntryBinding
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

class OrderEntryLastFragment : BaseFragment(R.layout.fragment_last_order_entry){
    lateinit var mBinding : FragmentLastOrderEntryBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var jsonObject: JSONObject
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

    lateinit var Dname: String
    lateinit var Dcontact: String
    lateinit var Dphone: String
    lateinit var Demail: String
    lateinit var Daddress1: String
    lateinit var Daddress2: String
    lateinit var Dcity: String
    lateinit var Dstate: String
    lateinit var DpostalCode: String
    lateinit var Dcountry: String


    private  var collectionStartDate: String=""
    private  var collectionEndDate: String=""
    private  var deliveryStartDate: String=""
    private  var deliveryEndDate: String=""
    private  var collectionStartDateTime: String=""
    private  var collectionEndDateTime: String=""
    private  var deliveryStartDateTime: String=""
    private  var deliveryEndDateTime: String=""
    var click = 0

    var mYear = 0
    var mMonth = 0
    var mDay = 0

    var mHour = 0
    var mMinute = 0

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentLastOrderEntryBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Order Entry"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

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

            Dname  = requireArguments().getString("Dname").toString()
            Dcontact  = requireArguments().getString("Dcontact").toString()
            Dphone  = requireArguments().getString("Dphone").toString()
            Demail  = requireArguments().getString("Demail").toString()
            Daddress1  = requireArguments().getString("Daddress1").toString()
            Daddress2  = requireArguments().getString("Daddress2").toString()
            Dcity  = requireArguments().getString("Dcity").toString()
            Dstate  = requireArguments().getString("Dstate").toString()
            DpostalCode  = requireArguments().getString("DpostalCode").toString()
            Dcountry  = requireArguments().getString("Dcountry").toString()
        }


        mBinding.btnSubmit.setOnClickListener {
       /*     if(mBinding.edtContact.text.toString().equals("")) Toast.makeText(context,"Please enter contact name",
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

            }*/
        }


        mBinding.tvColectionStartTime.setOnClickListener {
             click = 1
             datePicker()
        }

        mBinding.tvColectionEndTime.setOnClickListener {
            click = 1
            datePicker()
        }

        mBinding.tvDeliveryStartTime.setOnClickListener {
            click = 1
            datePicker()
        }

        mBinding.tvDeliveryEndTime.setOnClickListener {
            click = 4
            datePicker()
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
                    mBinding.tvName.text = jsonObject/*.getJSONArray("Contacts").getJSONObject(0)*/.getString("Name")
                    mBinding.tvAddress.text = jsonObject.getString("AddressLine1")  + ", " + jsonObject.getString("AddressLine2") + ", " + jsonObject.getString("City") + ", " + jsonObject.getString("State") + ", " + jsonObject.getString("Country")


                    mBinding.tvAccount.text = ":  "+jsonObject.getString("AccountNumber")
                    mBinding.tvPhone.text = ":  "+jsonObject.getString("Phone")
                    mBinding.tvEmail.text = ":  "+jsonObject.getString("Email")
                    mBinding.tvRequestedBy.text =  ":  "+jsonObject.getString("PrimaryContactName")
                    //  (mBinding.edtContact as TextView).text = jsonObject.getString("PrimaryContactName")






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


    private fun datePicker() {

        // Get Current Date
        val c: Calendar = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (click == 1) {
                    collectionStartDate =
                        year.toString()  + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    tiemPicker(collectionStartDate)
                } else if (click == 2) {
                    collectionEndDate =  year.toString()  + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    tiemPicker(collectionStartDate)
                } else if (click == 3) {
                    deliveryStartDate = year.toString()  + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    tiemPicker(collectionStartDate)
                } else if (click == 4) {
                    deliveryEndDate = year.toString()  + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString()
                    tiemPicker(collectionStartDate)
                }


                //*************Call Time Picker Here ********************

            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun tiemPicker(date : String) {
        // Get Current Time
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                if (click == 1) {
                    collectionStartDateTime = date + " " + hourOfDay + ":" + minute
                    mBinding.tvColectionStartTime.setText(date + " " + hourOfDay + ":" + minute)
                } else if (click == 2) {
                    collectionEndDateTime = date + " " + hourOfDay + ":" + minute
                    mBinding.tvColectionEndTime.setText(date + " " + hourOfDay + ":" + minute)
                } else if (click == 3) {
                    deliveryStartDateTime = date + " " + hourOfDay + ":" + minute
                    mBinding.tvDeliveryStartTime.setText(date + " " + hourOfDay + ":" + minute)
                } else if (click == 4) {
                    deliveryEndDateTime = date + " " + hourOfDay + ":" + minute
                    mBinding.tvDeliveryEndTime.setText(date + " " + hourOfDay + ":" + minute)
                }

            }, mHour, mMinute, false
        )
        timePickerDialog.show()
    }

}