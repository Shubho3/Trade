package com.nr.nrsales.utils

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.nr.nrsales.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity(val layoutId: Int) : AppCompatActivity(), View.OnClickListener {

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    abstract fun onBindTo(binding: ViewDataBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        //supportActionBar?.hide()
//        setNavigationColor(resources.getColor(R.color.app_color))
        // fullScreen()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )
        GlobalUtility.hideKeyboard(this)
        val binding: ViewDataBinding?
        if (layoutId != 0) {
            try {
                binding = DataBindingUtil.setContentView(this, layoutId)
                onBindTo(binding)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // getNetworkStateReceiver()
    }

    private fun fullScreen() {
        val window = window
        if (window != null) {
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    protected fun setNavigationColor(color: Int) {
        window?.navigationBarColor = color
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }


    override fun onClick(v: View) {
        GlobalUtility.avoidDoubleClicks(v)
    }

}