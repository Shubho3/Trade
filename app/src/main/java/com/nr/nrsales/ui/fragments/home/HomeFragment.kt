package com.nr.nrsales.ui.fragments.home

import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.util.Log
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.material.navigation.NavigationView
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentHomeBinding
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mBinding: FragmentHomeBinding
    private val viewmodel by viewModels<ProfileViewModel>()
    private val sharedPrf: SharedPrf  by lazy { SharedPrf(requireActivity()) }

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
                           mBinding.mailLayout.data = it.result[0]
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
    private fun setClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE)
                as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        GlobalUtility.showToast("Copied")
    }
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentHomeBinding
        mBinding.navView.setNavigationItemSelectedListener(this);
        init()
        getProfile()
    }

    private fun init() {

        mBinding.mailLayout.menu.setOnClickListener {
            mBinding.drawerLayout.open()
        }
        mBinding.mailLayout.addBtn.setOnClickListener {
            findNavController(it).navigate(R.id.action_homeFragment_to_getAQuoteFragment)
        }
        mBinding.mailLayout.acNo.setOnClickListener {
            setClipboard(requireActivity(), mBinding.mailLayout.acNo.text.toString())
        }
        mBinding.mailLayout.ifsc.setOnClickListener {
            setClipboard(requireActivity(), mBinding.mailLayout.ifsc.text.toString())
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.profile) {
            findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_editProfileFragment)

        } else if (item.itemId == R.id.add_fund) {
            findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_getAQuoteFragment)

        } else if (item.itemId == R.id.withdrawal_fund) {
            findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_invoiceFragment)

        } else if (item.itemId == R.id.live_market) {
            val ban =Bundle()
            ban.putString("url","1")
            findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_orderListFragment,ban)

        }  else if (item.itemId == R.id.live_position) {
            var ban =Bundle()
            ban.putString("url","https://www.moneycontrol.com//us-markets/index/chart?symbol=INDU")
            findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_orderListFragment,ban)

        } else if (item.itemId == R.id.logout) {
            mBinding.drawerLayout.close()
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage("are sure you want to logout").setCancelable(false).setPositiveButton(
                "yes"
            ) { dialog, _ ->
                dialog.cancel()
                GlobalUtility.showToast("Logout Successfully", getBaseContext())
                getSharedPrfData().setStoredTag(SharedPrf.LOGIN, "false")
                findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_splashFragment)
            }.setNegativeButton("no") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        return true
    }

}
