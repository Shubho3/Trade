package com.nr.nrsales.ui.fragments.adminhome

import android.Manifest
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.registerReceiver
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
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class AdminHomeFragment : BaseFragment(R.layout.fragment_admin_home) {
    private lateinit var mBinding: FragmentAdminHomeBinding
    private val viewmodel by viewModels<ProfileViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(requireActivity()) }
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    private lateinit var list: List<String>
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("TAG", "onReceManagePermissionsManagePermissionsManagePermissionsive: ", )
            getProfile()
        }
    }
    private fun getProfile() {
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchUserDashboard(map)
    }

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentAdminHomeBinding
        init()
        if (Build.VERSION.SDK_INT >= 33) {
            list = listOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
            managePermissions = ManagePermissions(
                requireActivity(), list, PermissionsRequestCode
            )
            managePermissions.checkPermissions()
        }


        // Register the receiver
        val filter = IntentFilter("com.example.UPDATE_UI_ACTION")
        requireActivity().registerReceiver(updateReceiver, filter)


    }
    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(updateReceiver)
    }
    override fun onResume() {

        super.onResume()

getProfile()



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
        viewmodel.userDashboardModel.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        Log.e("TAG", "init: $it")
                        if (it.status == "1") {
                            if (it.result[0].notification_count > 0) {
                                mBinding.tvCount.text = " ${it.result[0].notification_count} "
                                mBinding.tvCount.visibility = View.VISIBLE
                            } else mBinding.tvCount.visibility = View.GONE
                        } else {
                            mBinding.tvCount.visibility = View.GONE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                }
                is NetworkResult.Loading -> {
                }
            }
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