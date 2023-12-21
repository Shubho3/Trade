package com.nr.nrsales.ui.fragments.home

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
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
import com.nr.nrsales.viewmodel.LoginViewModel
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

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentHomeBinding
        mBinding.navView.setNavigationItemSelectedListener(this);

        val candleStickChart = mBinding.mailLayout.candleStickChart
        candleStickChart.isHighlightPerDragEnabled = true

        candleStickChart.setDrawBorders(true)

        context?.let { candleStickChart.setBorderColor(it.getColor(com.nr.nrsales.R.color.color_primary)) }

        val yAxis = candleStickChart.axisLeft
        val rightAxis = candleStickChart.axisRight
        yAxis.setDrawGridLines(false)
        rightAxis.setDrawGridLines(false)
        candleStickChart.requestDisallowInterceptTouchEvent(true)

        val xAxis = candleStickChart.xAxis

        xAxis.setDrawGridLines(false) // disable x axis grid lines

        xAxis.setDrawLabels(false)
        rightAxis.textColor = Color.WHITE
        yAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setAvoidFirstLastClipping(true)

        val l = candleStickChart.legend
        l.isEnabled = false
        val yValsCandleStick = ArrayList<CandleEntry>()
        yValsCandleStick.add(CandleEntry(0f, 225.0.toFloat(), 219.84.toFloat(), 224.94.toFloat(), 221.07.toFloat()))
        yValsCandleStick.add(CandleEntry(1f, 228.35.toFloat(), 222.57.toFloat(), 223.52.toFloat(), 226.41.toFloat()))
        yValsCandleStick.add(CandleEntry(2f, 226.84.toFloat(), 222.52.toFloat(), 225.75.toFloat(), 223.84.toFloat()))
        yValsCandleStick.add(CandleEntry(3f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(4f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(0f, 225.0.toFloat(), 219.84.toFloat(), 224.94.toFloat(), 221.07.toFloat()))
        yValsCandleStick.add(CandleEntry(1f, 228.35.toFloat(), 222.57.toFloat(), 223.52.toFloat(), 226.41.toFloat()))
        yValsCandleStick.add(CandleEntry(2f, 226.84.toFloat(), 222.52.toFloat(), 225.75.toFloat(), 223.84.toFloat()))
        yValsCandleStick.add(CandleEntry(3f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(4f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(0f, 225.0.toFloat(), 219.84.toFloat(), 224.94.toFloat(), 221.07.toFloat()))
        yValsCandleStick.add(CandleEntry(1f, 228.35.toFloat(), 222.57.toFloat(), 223.52.toFloat(), 226.41.toFloat()))
        yValsCandleStick.add(CandleEntry(2f, 226.84.toFloat(), 222.52.toFloat(), 225.75.toFloat(), 223.84.toFloat()))
        yValsCandleStick.add(CandleEntry(3f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(4f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        val set1 = CandleDataSet(yValsCandleStick, "DataSet 1")
        set1.color = Color.rgb(80, 80, 80)
        set1.shadowColor = resources.getColor(R.color.color_primary)
        set1.shadowWidth = 0.2f
        set1.decreasingColor = resources.getColor(R.color.color_red)
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = resources.getColor(R.color.color_blue)
        set1.increasingColor = resources.getColor(R.color.color_blue)
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.neutralColor = Color.LTGRAY
        set1.setDrawValues(false)


// create a data object with the datasets


// create a data object with the datasets
        val data = CandleData(set1)


// set data


// set data
        candleStickChart.data = data
        candleStickChart.invalidate()

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