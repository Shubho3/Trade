package com.nr.nrsales.ui.fragments

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentSplashBinding
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.SharedPrf

class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private lateinit var mBinding: FragmentSplashBinding
    private lateinit var sharedPref: SharedPrf
    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentSplashBinding
    }

    override fun onResume() {
        sharedPref = SharedPrf(requireContext())
        fullScreen()
        Handler(Looper.getMainLooper()).postDelayed({
          /*  val window = requireActivity().window
            if (window != null) {
                window.statusBarColor = requireActivity().getColor(R.color.white)
                WindowCompat.setDecorFitsSystemWindows(window, true)
            }*/
            if (sharedPref.getStoredTag(SharedPrf.LOGIN) == "true") {
                if (sharedPref.getStoredTag(SharedPrf.USER_TYPE) == "PROVIDER") {
                    Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_adminHomeFragment)
                } else {
                    Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_homeFragment)
                }
                val window = requireActivity().window
                if (window != null) {
                    window.statusBarColor = requireActivity().getColor(R.color.color_primary)
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                }
            } else {
                Navigation.findNavController(mBinding.root).navigate(R.id.action_splashFragment_to_LoginFragment)
                val window = requireActivity().window
                if (window != null) {
                    window.statusBarColor = requireActivity().getColor(R.color.color_primary)
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                }
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
