package com.nr.nrsales.ui.fragments.home

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
import coil.load
import com.bumptech.glide.Glide
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentEditProfileBinding
import com.nr.nrsales.databinding.FragmentViewProfileBinding
import com.nr.nrsales.model.User
import com.nr.nrsales.ui.LaunchingActivity
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.ManagePermissions
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.utils.customui.TouchImageView
import com.nr.nrsales.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class ViewProfileFragment : BaseFragment(R.layout.fragment_view_profile) {
    lateinit var mBinding: FragmentViewProfileBinding
    private lateinit var context: Context
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }

    override fun onBindTo(binding: ViewDataBinding?) {
        mBinding = binding as FragmentViewProfileBinding
        context = requireActivity()
        //val args = arguments
         //val driver = (args?.getParcelable("data") as User?)!!
        mBinding.user =sharedPrf.getUser2()
        init()
    }


    private fun init() {
        mBinding.headerLay.tvLogo.text = "Profile"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
mBinding.aadharFront.setOnClickListener { showData(sharedPrf.getUser2()?.aadhar_front) }
mBinding.aadharBack.setOnClickListener { showData(sharedPrf.getUser2()?.aadhar_back) }
mBinding.passbookFrontPage.setOnClickListener { showData("https://convertstext.net/unlock/uploads/images/"+sharedPrf.getUser2()?.passbook_photo) }
mBinding.panFront.setOnClickListener { showData(sharedPrf.getUser2()?.pan_front) }
mBinding.panBack.setOnClickListener { showData(sharedPrf.getUser2()?.pan_back) }

    }

    private fun showData(aadharFront: String?) {
        val dialog = Dialog(requireActivity(), R.style.DialogSlideAnim)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.show_image_dialog_2)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
        val trans = dialog.findViewById<View>(R.id.trans) as TouchImageView
        Glide.with(requireActivity()).load(aadharFront).into(trans)
        cont_find.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
