package com.nr.nrsales.ui.fragments.adminhome

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentAdminHomeBinding
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.ManagePermissions
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminHomeFragment : BaseFragment(R.layout.fragment_admin_home) {
    private lateinit var mBinding: FragmentAdminHomeBinding
    private val viewmodel by viewModels<ProfileViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(requireActivity()) }
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    private lateinit var list: List<String>


    private fun getProfile() {
        //GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchUserDashboard(map)
        viewmodel.userDashboardModel.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            if (it.result[0].notification_count > 0) {
                                mBinding.tvCount.text = " ${it.result[0].notification_count} "
                                mBinding.tvCount.visibility = View.VISIBLE
                            } else mBinding.tvCount.visibility = View.GONE
                            // mBinding.mailLayout.data = it.result[0]
                            //   mBinding.aadharFront.load(it.result.aadhar_front) { crossfade(true) }
                            //   mBinding.aadharBack.load(it.result.aadhar_back) { crossfade(true) }
                            //    mBinding.panFront.load(it.result.pan_front) { crossfade(true) }
                            //    mBinding.panBack.load(it.result.pan_back) { crossfade(true) }
                        } else {
                            mBinding.tvCount.visibility = View.GONE

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
        if (Build.VERSION.SDK_INT >= 33) {
            list = listOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
        managePermissions = ManagePermissions(
            requireActivity(), list, PermissionsRequestCode
        )
        managePermissions.checkPermissions()
    }

    override fun onResume() {
        getProfile()
        super.onResume()
    }

    private fun init() {
        mBinding.exit.setOnClickListener { logout() }
        mBinding.menu.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_notificationListFragment)
        }
        mBinding.userCard.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_usersListFragment)
        }
        mBinding.addFundCard.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_requestFundListFragment)
        }
        mBinding.withdrawalCard.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_withdrawalRequestFragment)
        }
        mBinding.settingsCard.setOnClickListener {
            findNavController(mBinding.root).navigate(R.id.action_adminHomeFragment_to_requestFundListFragment)
        }

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