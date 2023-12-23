package com.nr.nrsales.ui.fragments.adminhome

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentWithdrawalBinding
import com.nr.nrsales.databinding.FragmentWithdrawalRequestsBinding
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.model.OutFundResAdmin
import com.nr.nrsales.ui.adapter.OutFundAdapter
import com.nr.nrsales.ui.adapter.OutFundAdapterAdmin
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.viewmodel.home.OutFundViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody


@AndroidEntryPoint
class WithdrawalRequestFragment : BaseFragment(R.layout.fragment_withdrawal_requests) {
    lateinit var mBinding: FragmentWithdrawalRequestsBinding
    private lateinit var context: Context
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }
    private var funds: ArrayList<OutFundResAdmin.Result> = ArrayList()
    private lateinit var adapter: OutFundAdapterAdmin
    private val viewmodel by viewModels<OutFundViewModel>()
    private fun GetFund() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        map[" "] = " "
        viewmodel.fetchget_out_funds_list_admin(map)
        viewmodel.listResponseadmin.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            funds.clear()
                            funds.addAll(it.result)
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
        mBinding = binding as FragmentWithdrawalRequestsBinding
        context = requireActivity()
        init()
        adapter = OutFundAdapterAdmin(requireActivity(), funds)
        mBinding.historyRecycleView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter = adapter
        GetFund()
    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Withdrawal Amount"

        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }

    }

    companion object {
        val TAG = WithdrawalRequestFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): WithdrawalRequestFragment {
            val fragment = WithdrawalRequestFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
