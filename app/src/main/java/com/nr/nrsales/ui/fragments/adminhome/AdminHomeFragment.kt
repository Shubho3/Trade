package com.nr.nrsales.ui.fragments.adminhome

import android.app.AlertDialog
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentAdminHomeBinding
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminHomeFragment : BaseFragment(R.layout.fragment_admin_home) {
    private lateinit var mBinding: FragmentAdminHomeBinding
    private val viewmodel by viewModels<ProfileViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(requireActivity()) }
    private fun getProfile() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchUserDashboard(map)
        viewmodel.userDashboardModel.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            // mBinding.mailLayout.data = it.result[0]
                            //   mBinding.aadharFront.load(it.result.aadhar_front) { crossfade(true) }
                            //   mBinding.aadharBack.load(it.result.aadhar_back) { crossfade(true) }
                            //    mBinding.panFront.load(it.result.pan_front) { crossfade(true) }
                            //    mBinding.panBack.load(it.result.pan_back) { crossfade(true) }
                        } else {


                        }
                    }
                }

                is NetworkResult.Error -> {
                    GlobalUtility.hideProgressMessage()
                    Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)

                }

                is NetworkResult.Loading -> {
                    GlobalUtility.hideProgressMessage()
                }
            }
        }

    }

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentAdminHomeBinding
        init()
        getProfile()

    }

    private fun init() {
        mBinding.exit.setOnClickListener { logout() }

    }

    private fun logout() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage("are sure you want to logout").setCancelable(false).setPositiveButton(
            "yes"
        ) { dialog, _ ->
            dialog.cancel()
            GlobalUtility.showToast("Logout Successfully", getBaseContext())
            getSharedPrfData().setStoredTag(SharedPrf.LOGIN, "false")
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_splashFragment)
        }.setNegativeButton("no") { dialog, _ ->
            dialog.cancel()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}