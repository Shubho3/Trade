package com.nr.nrsales.ui.fragments.adminhome

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentAddFundBinding
import com.nr.nrsales.databinding.FragmentRequestFundListBinding
import com.nr.nrsales.databinding.FragmentUsersListBinding
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.model.User
import com.nr.nrsales.ui.adapter.FundAdapter
import com.nr.nrsales.ui.fragments.adminhome.adapter.UsersAdapter
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.ManagePermissions
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.viewmodel.home.AddFundViewModel
import com.nr.nrsales.viewmodel.home.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class UsersListFragment : BaseFragment(R.layout.fragment_users_list) {
    lateinit var mBinding: FragmentUsersListBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<UserViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }
  private var funds: ArrayList<User> = ArrayList()
    private lateinit var adapter: UsersAdapter

    private fun GetFund() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchget_all_user(map)
        viewmodel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            funds.clear()
                            funds.addAll(it.result)
                          //  funds.removeAt(0)
                            adapter.notifyDataSetChanged()
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
        mBinding = binding as FragmentUsersListBinding
        context = requireActivity()
        init()
        adapter = UsersAdapter(requireActivity(), funds)
        mBinding.historyRecycleView.layoutManager= LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter=adapter
        GetFund()
    }
    private fun init() {
        mBinding.headerLay.tvLogo.text = "Users"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

    }


    companion object {
        val TAG = UsersListFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): UsersListFragment {
            val fragment = UsersListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
