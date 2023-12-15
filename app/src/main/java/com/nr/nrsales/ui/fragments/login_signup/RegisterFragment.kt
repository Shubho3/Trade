package com.nr.nrsales.ui.fragments.login_signup

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentRegisterBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment(R.layout.fragment_register) {
    private lateinit var mBinding: FragmentRegisterBinding
    private val viewmodel by viewModels<RegisterViewModel>()
    private lateinit var sharedPrf: SharedPrf


    override fun onBindTo(binding: ViewDataBinding?) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mBinding = binding as FragmentRegisterBinding
        sharedPrf = SharedPrf(requireContext())
        init()
    }

    private fun init() {
        mBinding.signUpBtn.setOnClickListener {
            onClick(it)
            onBackPressed()

        }
        mBinding.headerLay.imgHeader.setOnClickListener {
            onClick(it)
            onBackPressed()

        }
        requireActivity().window?.statusBarColor = requireActivity().getColor(R.color.color_primary)
        mBinding.headerLay.tvLogo.text = "Create Account"
        mBinding.btnSubmit.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_registerFragment_to_homeFragment)

          /*  val name = mBinding.edtName.text.toString()
            val phone = mBinding.edtPhone.text.toString()
            val fax = mBinding.edtFax.text.toString()
            val email = mBinding.edtEmail.text.toString()
            val web = mBinding.edtWebsite.text.toString()
            val address_line_1 = mBinding.edtAddressLine1.text.toString()
            val address_line_2 = mBinding.edtAddressLine2.text.toString()
            val city = mBinding.edtCity.text.toString()
            val state = mBinding.edtState.text.toString()
            val postal_code = mBinding.edtPostalCode.text.toString()
            val country = mBinding.edtCountry.text.toString()
            val pass = mBinding.edtPass.text.toString()
            val confirm_pass = mBinding.edtPass2.text.toString()
            val map: HashMap<String, Any> = HashMap()
            map["Email"] = email
            map["username"] = email
            map["password"] = pass
            map["Name"] = name
            map["PrimaryContactName"] = name
            map["AddressLine1"] = address_line_1
            map["AddressLine2"] = address_line_2
            map["City"] = city
            map["State"] = state
            map["PostalCode"] = postal_code
            map["Country"] = country
            map["Phone"] = phone
            map["Fax"] = fax
            map["lat"] = ""
            map["lon"] = ""
            map["ios_register_id"] = ""
            map["register_id"] = ""
            map["country_code"] = ""
       */     //viewmodel.fetchRegisterResponse(map)
/*            viewmodel._response.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        GlobalUtility.hideProgressMessage()
                        response.data?.let {
                            Toast.makeText(context, "User Created Successfully", Toast.LENGTH_SHORT).show()
                            sharedPrf.setStoredTag(SharedPrf.LOGIN, "true")
                            sharedPrf.setStoredTag(SharedPrf.USER_ID, it.ID!!)
                            Log.e(LoginFragment.TAG, "init: " + sharedPrf.getStoredTag(SharedPrf.USER_ID))
                            // GlobalUtility.showSuccessMessage(requireActivity(),sharedPrf.getStoredTag(SharedPrf.USER_ID).toString(),"msgggg")
                            findNavController(mBinding.root).navigate(R.id.action_registerFragment_to_homeFragment)
                        }
                    }

                    is NetworkResult.Error -> {
                        GlobalUtility.hideProgressMessage()
                        Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                        Toast.makeText(
                            context,
                            "User Not Found",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                        GlobalUtility.hideProgressMessage()
                    }

                    else -> {
                        GlobalUtility.hideProgressMessage()

                    }
                }
            }*/
        }


    }

    companion object {
        val TAG = RegisterFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): RegisterFragment {
            val fragment = RegisterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}