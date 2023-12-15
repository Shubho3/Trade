package com.nr.nrsales.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment(private val layoutId: Int) : Fragment(), View.OnClickListener {
    private lateinit var mBinding: ViewDataBinding
    private val mActivity by lazy { requireActivity() }
    private val sharedPrf by lazy { SharedPrf(requireContext()) }
    private val backDispatcher by lazy { requireActivity().onBackPressedDispatcher }

    protected abstract fun onBindTo(binding: ViewDataBinding?)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindTo(mBinding)
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        activity?.let { GlobalUtility.hideKeyboard(it) }
    }

    private fun fullScreen() {
        val window = mActivity.window
        if (window != null) {
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onClick(v: View) {
        activity?.let { GlobalUtility.hideKeyboard(it) }
        GlobalUtility.avoidDoubleClicks(v)
        GlobalUtility.btnClickAnimation(v)
    }

    protected open fun hideSoftInputWindow(view: View) {
        val imm = view.context
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun onBackPressed() {
        backDispatcher.onBackPressed()
    }

    fun getSharedPrfData(): SharedPrf {
        return sharedPrf
    }
    fun getBaseContext(): Context {
        return mActivity
    }
}