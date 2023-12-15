package com.nr.nrsales.ui.fragments.home

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.core.view.get
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentHomeBinding
import com.nr.nrsales.utils.BaseActivity.Companion.TAG
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.viewmodel.LoginViewModel


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var mBinding: FragmentHomeBinding
    private val viewmodel by viewModels<LoginViewModel>()

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentHomeBinding

        val candleStickChart=mBinding.mailLayout.candleStickChart
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
        yValsCandleStick.add(CandleEntry(0f, 225.0.toFloat(), 219.84.toFloat(), 224.94.toFloat(),  221.07.toFloat()))
        yValsCandleStick.add(CandleEntry(1f, 228.35.toFloat(), 222.57.toFloat(), 223.52.toFloat(), 226.41.toFloat()))
        yValsCandleStick.add(CandleEntry(2f, 226.84.toFloat(), 222.52.toFloat(), 225.75.toFloat(), 223.84.toFloat()))
        yValsCandleStick.add(CandleEntry(3f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(4f, 232.95.toFloat(), 217.27.toFloat(), 222.15.toFloat(), 217.88.toFloat()))
        yValsCandleStick.add(CandleEntry(5f, 232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(6f, 232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(7f, 232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(8f, 232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(10f,232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(10f,232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
        yValsCandleStick.add(CandleEntry(10f,232.95.toFloat(), 217.27.toFloat(), 322.15.toFloat(),317.88.toFloat()))
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
    }

    private fun init() {
        mBinding.navView.menu[0].setOnMenuItemClickListener { item ->
            if (item.itemId == com.nr.nrsales.R.id.logout) {
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
            }else if (item.itemId==R.id.profile){
                findNavController(mBinding.root).navigate(R.id.action_homeFragment_to_editProfileFragment)

            }
            true
        }
        mBinding.mailLayout.menu.setOnClickListener {
            mBinding.drawerLayout.open()/*if (mBinding.drawerLayout.isDrawerOpen(it)){

            }else{

            }*/
        }
        mBinding.mailLayout.getAQuoteCard.setOnClickListener {
            Log.e(TAG, "init: " + getSharedPrfData().getStoredTag(SharedPrf.USER_ID))
            Log.e(TAG, "init: " + getBaseContext().getString(R.string.email_id))
            findNavController(it).navigate(R.id.action_homeFragment_to_getAQuoteFragment)

        }


//        mBinding.mailLayout.orderEntryCard.setOnClickListener {
//            Log.e(TAG, "init: " + getSharedPrfData().getStoredTag(SharedPrf.USER_ID))
//            Log.e(TAG, "init: " + getBaseContext().getString(R.string.email_id))
//            findNavController(it).navigate(R.id.action_homeFragment_to_orderEntryFragment)
//
//        }


        mBinding.mailLayout.editCard.setOnClickListener {
            findNavController(it).navigate(R.id.action_homeFragment_to_editProfileFragment)

        }


        mBinding.mailLayout.trackingCard.setOnClickListener {
            findNavController(it).navigate(R.id.action_homeFragment_to_orderListFragment)

        }




        mBinding.mailLayout.invoicesCard.setOnClickListener {
            findNavController(it).navigate(R.id.action_homeFragment_to_invoiceFragment)

        }


    }

}