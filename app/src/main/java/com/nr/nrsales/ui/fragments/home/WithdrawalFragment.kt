package com.nr.nrsales.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentWithdrawalBinding
import com.nr.nrsales.model.OutFundRes
import com.nr.nrsales.ui.adapter.OutFundAdapter
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
class WithdrawalFragment : BaseFragment(R.layout.fragment_withdrawal) {
    lateinit var mBinding: FragmentWithdrawalBinding
    private lateinit var context: Context
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }
    private var funds: ArrayList<OutFundRes.Result> = ArrayList()
    private lateinit var adapter: OutFundAdapter
    private val viewmodel by viewModels<OutFundViewModel>()
    private fun GetFund() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchout_funds_list(map)
        viewmodel.listResponse.observe(viewLifecycleOwner) { response ->
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
        mBinding = binding as FragmentWithdrawalBinding
        context = requireActivity()
        init()
        adapter = OutFundAdapter(requireActivity(), funds)
        mBinding.historyRecycleView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter = adapter
        GetFund()
    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Withdrawal Amount"

        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        mBinding.btnSubmit.setOnClickListener {
            if (!Validation.getNormalValidCheck(mBinding.edtTransId)) {
                return@setOnClickListener
            } else if (!Validation.getNormalValidCheck(mBinding.edtAmount)) {
                return@setOnClickListener
            } else {
                GlobalUtility.showProgressMessage(requireActivity(), "Uploading Data...")
                val bodyx: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                bodyx.addFormDataPart("user_id", sharedPrf.getStoredTag(SharedPrf.USER_ID))
                    .addFormDataPart("amount", mBinding.edtAmount.text.toString())
                    .addFormDataPart("amount_pass", mBinding.edtTransId.text.toString()).build()
                viewmodel.fetchAddFunds(bodyx.build())

            }
            viewmodel.response.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        GlobalUtility.hideProgressMessage()
                        response.data?.let {
                            Toast.makeText(context, "Fund Withdrawal Successfully", Toast.LENGTH_SHORT).show()
                            mBinding.edtTransId.setText("")
                            mBinding.edtAmount.setText("")
                            mBinding.imgReceipt.setImageDrawable(requireActivity().getDrawable(R.drawable.baseline_add_a_photo))
                            GetFund()
                        }
                    }

                    is NetworkResult.Error -> {
                        GlobalUtility.hideProgressMessage()
                        Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                        GetFund()

                    }

                    is NetworkResult.Loading -> {
                        GlobalUtility.hideProgressMessage()
                    }

                    else -> {
                        GlobalUtility.hideProgressMessage()

                    }
                }
            }

        }

    }

    companion object {
        val TAG = WithdrawalFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): WithdrawalFragment {
            val fragment = WithdrawalFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
