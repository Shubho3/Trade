package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.google.gson.JsonObject
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentEditProfileBinding
import com.nr.nrsales.ui.LaunchingActivity
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.yayatotaxi.retrofit.ApiClient
import com.yayatotaxi.retrofit.NRApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    lateinit var mBinding: FragmentEditProfileBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private lateinit var jsonObject: JSONObject
    lateinit var jsonObjectMain : JsonObject
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentEditProfileBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Profile"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

      //  getUserProfile()


        mBinding.btnUpdate.setOnClickListener {

      /*      if(mBinding.edtName.text.toString().equals("")) Toast.makeText(context,"Please enter name",Toast.LENGTH_LONG).show()
            else if(mBinding.edtContact.text.toString().equals("")) Toast.makeText(context,"Please enter contact",Toast.LENGTH_LONG).show()
            else if(mBinding.edtPhone.text.toString().equals("")) Toast.makeText(context,"Please enter Phone",Toast.LENGTH_LONG).show()
            else if(mBinding.edtFax.text.toString().equals("")) Toast.makeText(context,"Please enter Fax",Toast.LENGTH_LONG).show()
            else if(mBinding.edtEmail.text.toString().equals("")) Toast.makeText(context,"Please enter Email",Toast.LENGTH_LONG).show()
            else if(mBinding.edtWebsite.text.toString().equals("")) Toast.makeText(context,"Please enter Website",Toast.LENGTH_LONG).show()
            else if(mBinding.edtAddressLineOne.text.toString().equals("")) Toast.makeText(context,"Please enter Address line1",Toast.LENGTH_LONG).show()
            else if(mBinding.edtAddressLineTwo.text.toString().equals("")) Toast.makeText(context,"Please enter Address line2",Toast.LENGTH_LONG).show()
            else if(mBinding.edtCity.text.toString().equals("")) Toast.makeText(context,"Please enter City",Toast.LENGTH_LONG).show()
            else if(mBinding.edtSate.text.toString().equals("")) Toast.makeText(context,"Please enter State",Toast.LENGTH_LONG).show()
            else if(mBinding.edtPostalCode.text.toString().equals("")) Toast.makeText(context,"Please enter Postal Code",Toast.LENGTH_LONG).show()
            else if(mBinding.edtCountry.text.toString().equals("")) Toast.makeText(context,"Please enter Country",Toast.LENGTH_LONG).show()
            else// updateCustomerProfile()

 */       }

        mBinding.btnLogout.setOnClickListener {
            sharedPrf.clearAll()
            startActivity(Intent(activity, LaunchingActivity::class.java))
            requireActivity().finish()
        }


    }

/*

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


                    (mBinding.edtName as TextView).text  =    jsonObject.*/
/*getJSONArray("Contacts").getJSONObject(0).*//*
getString("Name")
                    (mBinding.edtContact as TextView).text = jsonObject.getString("PrimaryContactName")
                    (mBinding.edtPhone as TextView).text = jsonObject.getString("Phone")
                    (mBinding.edtFax as TextView).text = jsonObject.getString("Fax")
                    (mBinding.edtEmail as TextView).text = jsonObject.getString("Email")
                    (mBinding.edtWebsite as TextView).text = jsonObject.getString("Website")
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


    private  fun updateCustomerProfile() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        //val js = JSONArray()

        var api = activity?.let { ApiClient.getClientOne(it) }!!.create(NRApiService::class.java)
              // sharedPrf.getStoredTag( SharedPrf.USER_ID)
        val map: HashMap<String, Any> = HashMap()

        //map["PriceSets"] = jsonObject.getJSONArray("PriceSets")
       // map["DefaultPriceSet"] = jsonObject.getString("DefaultPriceSet")

      //  map["Departments"] = jsonObject.getJSONArray("Departments")
      //  map["Contacts"] = jsonObject.getJSONArray("Contacts")
        map["ID"] =  sharedPrf.getStoredTag(SharedPrf.USER_ID)//jsonObject.getString("ID")
        map["Name"] = jsonObject.getString("Name").toString()
        map["PrimaryContactName"] = mBinding.edtContact.text.toString()
        map["AddressLine1"] = mBinding.edtAddressLineOne.text.toString()
        map["AddressLine2"] = mBinding.edtAddressLineTwo.text.toString()
        map["City"] = mBinding.edtCity.text.toString()
        map["State"] = mBinding.edtSate.text.toString()
        map["PostalCode"] = mBinding.edtPostalCode.text.toString()
        map["Country"] = mBinding.edtCountry.text.toString()
        map["Phone"] = mBinding.edtPhone.text.toString()
        map["Fax"] = mBinding.edtFax.text.toString()

        map["Email"] = mBinding.edtEmail.text.toString()
        map["Website"] = mBinding.edtWebsite.text.toString()
        map["AccountNumber"] = jsonObject.getString("AccountNumber").toString()
        */
/*map["DestinationCategories"] = jsonObject.getString("DestinationCategories")
        map["BillingAdjustmentPercentage"] = jsonObject.getDouble("BillingAdjustmentPercentage")
        map["SendInvoiceByEmail"] = jsonObject.getBoolean("SendInvoiceByEmail")
        map["SendInvoiceByPostal"] = jsonObject.getBoolean("SendInvoiceByPostal")
        map["SendInvoiceByFax"] = jsonObject.getBoolean("SendInvoiceByFax")
        map["HasWebPortalAccess"] = jsonObject.getBoolean("HasWebPortalAccess")
        map["BillingEmail"] = jsonObject.getString("BillingEmail")
        map["TimeZone"] = jsonObject.getString("TimeZone")
        map["ReferenceNumber"] = jsonObject.getString("ReferenceNumber")
        map["PurchaseOrderNumber"] = jsonObject.getString("PurchaseOrderNumber")
        map["Comments"] = jsonObject.getString("Comments")
        map["OrderEntryRequiredFields"] = jsonObject.getString("OrderEntryRequiredFields")
        map["Inactive"] = jsonObject.getBoolean("Inactive")
        map["PricingMethod"] = jsonObject.getInt("PricingMethod")*//*

        val mapMain: HashMap<String, Any> = HashMap()
        //mapMain["customer"] = map.toString()

        val call = createJsonRequestBody(map)?.let {
            api.updateCustomerProfile("867bb48f-ade8-4688-954b-12668ea07977",createJsonRequestBody(map))
        }


        //val call = api.updateCustomerProfile("867bb48f-ade8-4688-954b-12668ea07977",)
        Log.e(OrderEntryFragment.TAG, "EditProfileRequest: "+ createJsonRequestBody(map))

        if (call != null) {
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    try {
                        GlobalUtility.hideProgressMessage()
                        //  val object1 = JSONObject(Gson().toJson(response.body()))
                        // Log.e("TAG", "onResponse: $object1")


                        val stringRes = response.body()!!.string()
                        Log.e("Edit Profile Response", "stringRes = ${stringRes}")
                        // jsonObject = JSONObject(stringRes)
                        //  if (jsonObject.getString("status").equals("1")) {
                        //  modelLogin = Gson().fromJson(stringRes, CsvResModel2::class.java)
                        // Log.e("jafhkjdf", "stringRes = ${modelLogin.getResult().toString()}")
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                         onBackPressed()

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
    }





    fun createJsonRequestBody(params: HashMap<String, Any>) = (params as Map<*,* >?)?.let { JSONObject(it).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()) }

*/

}
