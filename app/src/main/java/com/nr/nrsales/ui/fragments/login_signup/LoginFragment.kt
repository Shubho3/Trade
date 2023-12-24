package com.nr.nrsales.ui.fragments.login_signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.nr.nrsales.MainApplication
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentLoginBinding
import com.nr.nrsales.pushnotification.MyFirebaseMessagingService.Companion.getFirebaseToken
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<LoginViewModel>()
    private lateinit var sharedPrf: SharedPrf
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentLoginBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
        init()

    }


    private fun init() {
        getFirebaseToken(requireActivity())
        mBinding.signUpBtn.setOnClickListener {
            Navigation.findNavController(mBinding.root)
                .navigate(R.id.action_LoginFragment_to_signupFragment)
        }
        mBinding.btnSubmit.setOnClickListener {
            //
            // onClick(mBinding.btnSubmit)
            val email = mBinding.edtEmail.text.toString()
            val pass = mBinding.edtPass.text.toString()
            if (email.isEmpty()) {
                mBinding.edtEmail.text = (null)
                GlobalUtility.showToast(getString(R.string.invalid_email), context)
                mBinding.edtEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                GlobalUtility.showToast(getString(R.string.valid_email), context)
                mBinding.edtEmail.requestFocus()
            } else if (pass.isEmpty()) {
                mBinding.edtEmail.requestFocus()
                GlobalUtility.showToast(getString(R.string.invalid_password), context)
            } else if (pass.length <= 3) {
                mBinding.edtEmail.requestFocus()
                GlobalUtility.showToast(getString(R.string.invalid_password_length), context)
            } else {
                if (!MainApplication.hasInternetConnection(requireContext())) {
                    GlobalUtility.showNoNetworkMessage(requireActivity())
                } else {
                    GlobalUtility.showProgressMessage(
                        requireActivity(),
                        requireActivity().getString(R.string.loading)
                    )
                    val map: HashMap<String, Any> = HashMap()
                    map["email"] = email
                    map["password"] = pass
                    map["register_id"] = sharedPrf.getStoredTag(SharedPrf.FIREBASE_TOKEN)
                    viewmodel.fetchLoginResponse(map)

                    viewmodel.response.observe(viewLifecycleOwner) { response ->
                        when (response) {
                            is NetworkResult.Success -> {
                                GlobalUtility.hideProgressMessage()
                                response.data?.let {
                                    if (it.status == "1") {
                                        Toast.makeText(
                                            context,
                                            "Login Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        sharedPrf.setStoredTag(SharedPrf.LOGIN, "true")
                                        sharedPrf.setStoredTag(SharedPrf.USER_ID, it.result.id)
                                        sharedPrf.setStoredTag(SharedPrf.USER_TYPE, it.result.type)
                                        sharedPrf.setUser(it.result)
                                        Log.e(
                                            TAG,
                                            "init: " + sharedPrf.getStoredTag(SharedPrf.USER_ID)
                                        )
                                        if (it.result.type == "PROVIDER") {
                                            Navigation.findNavController(mBinding.root)
                                                .navigate(R.id.action_loginFragment_to_adminHomeFragment)
                                        } else {
                                            Navigation.findNavController(mBinding.root)
                                                .navigate(R.id.action_loginFragment_to_homeFragment2)
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            it.message.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                }
                            }

                            is NetworkResult.Error -> {
                                GlobalUtility.hideProgressMessage()
                                Log.e(TAG, "fetchLoginResponse: " + response.message)
                                Toast.makeText(
                                    context,
                                    "Your have entered wrong email & password",
                                    Toast.LENGTH_LONG
                                ).show()
                                viewmodel.response.removeObservers(viewLifecycleOwner)
                            }

                            is NetworkResult.Loading -> {
                                GlobalUtility.hideProgressMessage()
                            }

                            else -> {
                                GlobalUtility.hideProgressMessage()
                                Log.e(TAG, "fetchLoginResponse: " + response.message)
                                Toast.makeText(
                                    context,
                                    "Your have entered wrong email & password",
                                    Toast.LENGTH_LONG
                                ).show()
                                viewmodel.response.removeObservers(viewLifecycleOwner)

                            }
                        }
                    }

                }

            }
        }
    }

    companion object {
        val TAG = LoginFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): LoginFragment {
            val fragment = LoginFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
