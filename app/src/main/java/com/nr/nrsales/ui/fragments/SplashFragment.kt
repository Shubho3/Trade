package com.nr.nrsales.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentHomeBinding
import com.nr.nrsales.databinding.FragmentSplashBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.SharedPrf

class SplashFragment : BaseFragment(R.layout.fragment_splash) {
     private lateinit var mBinding :FragmentSplashBinding
    private lateinit var sharedPref: SharedPrf
    override fun onBindTo(binding: ViewDataBinding?) {
                mBinding = binding as FragmentSplashBinding
    }
    override fun onResume() {
        sharedPref = SharedPrf(requireContext())
        fullScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            val window = requireActivity().window
            if (window != null) {
                window.statusBarColor = requireActivity().getColor(R.color.color_primary)
                WindowCompat.setDecorFitsSystemWindows(window, true)

            }
            if (sharedPref.getStoredTag(SharedPrf.LOGIN) == "true") {
               // Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_orderListFragment)

                Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                 Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_LoginFragment)

             //   Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_invoiceFragment)

             //  Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_orderListFragment)




            }
        }, 3000)
        super.onResume()
    }
    private fun fullScreen() {
        val window = requireActivity().window
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
           /* window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          */  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}