package com.nr.nrsales.ui.fragments.adminhome

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nr.nrsales.R
import com.nr.nrsales.databinding.AddPositionDialogBinding
import com.nr.nrsales.databinding.FragmentUsersListBinding
import com.nr.nrsales.model.User
import com.nr.nrsales.ui.fragments.adminhome.adapter.UsersAdapter
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.utils.customui.TouchImageView
import com.nr.nrsales.viewmodel.home.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersListFragment : BaseFragment(R.layout.fragment_users_list),
    SearchView.OnQueryTextListener, UsersAdapter.UsersAdapterClickListener {
    lateinit var mBinding: FragmentUsersListBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<UserViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }
    private var funds: ArrayList<User> = ArrayList()
    private lateinit var adapter: UsersAdapter

    private fun GetFund() {
        GlobalUtility.showProgressMessage(
            requireActivity(),
            requireActivity().getString(R.string.loading)
        )
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
                            adapter.addData(funds)
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
        adapter = UsersAdapter(requireActivity(), this)
        mBinding.historyRecycleView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter = adapter
        GetFund()
    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Users"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        mBinding.searchBar.setOnQueryTextListener(this)
    }


    companion object {
        val TAG = UsersListFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): UsersListFragment {
            val fragment = UsersListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onItemClick(model: User, int: Int) {
        if(int ==11){
        //    val b = Bundle()
       //     b.putParcelable("data",model)
           sharedPrf.setUser2(model)
            Navigation.findNavController(mBinding.root).navigate(R.id.action_usersListFragment_to_viewProfileFragmentx)
        }else
        if (int == 0) {
            val dialog = Dialog(requireActivity(), R.style.DialogSlideAnim)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.add_position_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val accept = dialog.findViewById<View>(R.id.accept) as Button
            val edtAmount = dialog.findViewById<View>(R.id.edt_amount) as EditText
            val edtShareName = dialog.findViewById<View>(R.id.edt_share_name) as EditText
            val edtSharePosition = dialog.findViewById<View>(R.id.edt_share_position) as EditText
            val edtProfit = dialog.findViewById<View>(R.id.edt_profit) as EditText
            val edtLoss = dialog.findViewById<View>(R.id.edt_loss) as EditText
            val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
            accept.setOnClickListener {
                if (!Validation.getNormalValidCheck(edtShareName)) {
                    return@setOnClickListener
                } else if (!Validation.getNormalValidCheck(edtSharePosition)) {
                    return@setOnClickListener
                } else if (!Validation.getNormalValidCheck(edtAmount)) {
                    return@setOnClickListener
                } else  if (!Validation.getNormalValidCheck(edtProfit)) {
                    return@setOnClickListener
                } else  if (!Validation.getNormalValidCheck(edtLoss)) {
                    return@setOnClickListener
                } else {
                    GlobalUtility.showProgressMessage(requireActivity(), "Uploading Data...")
                    val map: HashMap<String, Any> = HashMap()
                    map["user_id"] = model.id
                    map["share_amount"] = edtAmount.text.toString()
                    map["share_name"] = edtShareName.text.toString()
                    map["share_position"] = edtSharePosition.text.toString()
                    map["profit"] = edtProfit.text.toString()
                    map["loss"] = edtLoss.text.toString()
                    viewmodel.fetch_add_position(map)
                }
                viewmodel.response.observe(this) { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            GlobalUtility.hideProgressMessage()
                            response.data?.let {
                                Toast.makeText(
                                    context,
                                    "Position Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        is NetworkResult.Error -> {
                            GlobalUtility.hideProgressMessage()
                            Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                        }

                        is NetworkResult.Loading -> {
                            GlobalUtility.hideProgressMessage()
                        }

                        else -> {
                            GlobalUtility.hideProgressMessage()

                        }
                    }
                }

                dialog.dismiss()
            }
            cont_find.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        else {
            val dialog = Dialog(requireActivity(), R.style.DialogSlideAnim)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.change_status_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val accept = dialog.findViewById<View>(R.id.accept) as Button
            val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
            accept.setOnClickListener {
                GlobalUtility.showProgressMessage(requireActivity(), "Uploading Data...")
                val map: HashMap<String, Any> = HashMap()
                map["user_id"] = model.id
                if (model.status == "1") map["status"] = 0
                else map["status"] = 1
                viewmodel.update_user_status(map)
                viewmodel.update_user_status.observe(this) { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            GlobalUtility.hideProgressMessage()
                            response.data?.let {
                                Toast.makeText(
                                    context,
                                    "Status Updated Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
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

                        else -> {
                            GlobalUtility.hideProgressMessage()

                        }
                    }
                }
                dialog.dismiss()
            }
            cont_find.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }


}
