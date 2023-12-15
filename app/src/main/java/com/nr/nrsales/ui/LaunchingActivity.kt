package com.nr.nrsales.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nr.nrsales.R
import com.nr.nrsales.databinding.ActivityLaunchingBinding
import com.nr.nrsales.utils.BaseActivity

class LaunchingActivity : BaseActivity(R.layout.activity_launching) {
    private lateinit var mBinding: ActivityLaunchingBinding
    private lateinit var navController: NavController
    companion object {
        val TAG: String = ActivityLaunchingBinding::class.java.simpleName
    }
    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as ActivityLaunchingBinding

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment? ?: return
        navController = host.navController
    }
}