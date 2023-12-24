package com.nr.nrsales.ui.fragments.adminhome

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentWithdrawalBinding
import com.nr.nrsales.databinding.FragmentWithdrawalRequestsBinding
import com.nr.nrsales.model.AddFundRes
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
import com.nr.nrsales.utils.customui.TouchImageView
import com.nr.nrsales.viewmodel.home.OutFundViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody


@AndroidEntryPoint
class WithdrawalRequestFragment : BaseFragment(R.layout.fragment_withdrawal_requests) ,OutFundAdapterAdmin.outFundClickListener{
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
        adapter = OutFundAdapterAdmin(requireActivity(), funds,this)
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

    override fun onItemClick(model: OutFundResAdmin.Result, int: Int) {
        val dialog = Dialog(requireActivity(), R.style.DialogSlideAnim)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.show_image_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val accept = dialog.findViewById<View>(R.id.accept) as Button
        val reject = dialog.findViewById<View>(R.id.reject) as Button
        val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
        val trans = dialog.findViewById<View>(R.id.trans) as TouchImageView
        trans.visibility=View.GONE
      //  Glide.with(requireActivity()).load(model.payment_receipt).into(trans)
        reject.setOnClickListener {
            accept_reject(model.id, "2");
            dialog.dismiss()
        }
        accept.setOnClickListener {
            accept_reject(model.id, "1");
            dialog.dismiss()
        }
        cont_find.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun accept_reject(fund_id: String, status: String) {
        GlobalUtility.showProgressMessage(
            requireActivity(), requireActivity().getString(R.string.loading)
        )
        val map: HashMap<String, Any> = HashMap()
        //  map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        map["id"] = fund_id
        map["status"] = status
        viewmodel.out_funt_accept_reject(map)
        viewmodel.outlistResponseadmin.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        GetFund()
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

}
