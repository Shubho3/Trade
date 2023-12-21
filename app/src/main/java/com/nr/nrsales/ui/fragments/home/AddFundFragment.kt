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
import com.nr.nrsales.model.AddFundRes
import com.nr.nrsales.ui.adapter.FundAdapter
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.ManagePermissions
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.viewmodel.home.AddFundViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class AddFundFragment : BaseFragment(R.layout.fragment_add_fund) {
    lateinit var mBinding: FragmentAddFundBinding
    private lateinit var context: Context
    private val viewmodel by viewModels<AddFundViewModel>()
    private val sharedPrf: SharedPrf by lazy { SharedPrf(context) }
    private val REQUEST_CODE = 123
    private lateinit var managePermissions: ManagePermissions
    private var aadhar_front: String = ""
    private lateinit var list: List<String>
    private var funds: ArrayList<AddFundRes.Result> = ArrayList()
    private lateinit var adapter: FundAdapter

    private fun GetFund() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchGetFund(map)
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
        mBinding = binding as FragmentAddFundBinding
        context = requireActivity()
        init()
        if (Build.VERSION.SDK_INT >= 33) {
            list = listOf(
                Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            list = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }
        managePermissions = ManagePermissions(requireActivity(), list, REQUEST_CODE)
        managePermissions.checkPermissions()
        adapter = FundAdapter(requireActivity(), funds)
        mBinding.historyRecycleView.layoutManager= LinearLayoutManager(requireActivity())
        mBinding.historyRecycleView.adapter=adapter
        GetFund()
    }

    private var cameraResultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val photo = data!!.extras!!["data"] as Bitmap?
            val cameraPath: String = "" + photo?.let { GlobalUtility.saveToInternalStorage(it, requireActivity()) }
            Log.e("PATH Camera", "" + cameraPath)
            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeFile(cameraPath, o)
            val REQUIRED_SIZE = 1024
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            val bitmap = BitmapFactory.decodeFile(cameraPath, o2)
            aadhar_front = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
            Log.e("ImagePath", "onActivityResult: $aadhar_front")
            mBinding.imgReceipt.load(bitmap) { crossfade(true) }
        }
    }
    private var galleryResultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (Build.VERSION.SDK_INT >= 33) {
                val selectedImage = data!!.data
                try {
                    assert(selectedImage != null)
                    if (selectedImage != null) {
                        requireActivity().contentResolver.openInputStream(selectedImage).use { stream ->
                            val bitmap = BitmapFactory.decodeStream(stream)
                            val tempfile: File = GlobalUtility.persistImage(bitmap, requireActivity())
                            aadhar_front = tempfile.absolutePath
                            Log.e("ImagePath", "onActivityResult: $aadhar_front")
                            mBinding.imgReceipt.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: IOException) {
                    e.localizedMessage?.let { GlobalUtility.showToast(it, requireActivity()) }
                }
            } else {
                val selectedImage = data!!.data
                val ImagePath: String = GlobalUtility.getPath(selectedImage, requireActivity())
                Log.e("TAG", ":ImagePathImagePathImagePath -- " + ImagePath)
                // Decode image size
                val o = BitmapFactory.Options()
                o.inJustDecodeBounds = true
                BitmapFactory.decodeFile(ImagePath, o)
                val REQUIRED_SIZE = 1024
                var width_tmp = o.outWidth
                var height_tmp = o.outHeight
                var scale = 1
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) break
                    width_tmp /= 2
                    height_tmp /= 2
                    scale *= 2
                }
                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                val bitmap = BitmapFactory.decodeFile(ImagePath, o2)
                aadhar_front = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
                Log.e("ImagePath", "onActivityResult: $aadhar_front")
                mBinding.imgReceipt.setImageBitmap(bitmap)
            }
        }
    }

    private fun hasStoragePermission(context: Context): Boolean {
        val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }

        return ContextCompat.checkSelfPermission(
            context, storagePermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun init() {
        mBinding.headerLay.tvLogo.text = "Add Funds"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        mBinding.btnSubmit.setOnClickListener {
            if (!Validation.getNormalValidCheck(mBinding.edtTransId)) {
                return@setOnClickListener
            } else if (!Validation.getNormalValidCheck(mBinding.edtAmount)) {
                return@setOnClickListener
            } else if (!Validation.getStringEmptyCheck(aadhar_front, "Please Pick Receipt Picture")) {
                return@setOnClickListener
            } else {
                GlobalUtility.showProgressMessage(requireActivity(), "Uploading Data...")
                val bodyx: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                bodyx.addFormDataPart("user_id", sharedPrf.getStoredTag(SharedPrf.USER_ID)).addFormDataPart("amount_id", mBinding.edtAmount.text.toString())
                    .addFormDataPart("transaction_id", mBinding.edtTransId.text.toString()).build()
                if (aadhar_front != "") bodyx.addPart(
                    MultipartBody.Part.createFormData(
                        "receipt", mBinding.edtTransId.text.toString() + "Receipt.png", RequestBody.create("image/*".toMediaTypeOrNull(), File(aadhar_front))
                    )
                )
                viewmodel.fetchAddFunds(bodyx.build())

            }
            viewmodel.response.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        GlobalUtility.hideProgressMessage()
                        response.data?.let {
                            Toast.makeText(context, "Fund Added Successfully", Toast.LENGTH_SHORT).show()
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

        mBinding.recLay.setOnClickListener {
            val dialog = Dialog(requireActivity(), R.style.DialogSlideAnim)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.select_img_lay)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val camera = dialog.findViewById<View>(R.id.camera) as Button
            val gallary = dialog.findViewById<View>(R.id.gallary) as Button
            val cont_find = dialog.findViewById<View>(R.id.cont_find) as TextView
            gallary.setOnClickListener {
                dialog.dismiss()
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                if (hasStoragePermission(requireActivity())) {
                    galleryResultLauncher2.launch(i)
                } else {
                    managePermissions.checkPermissions()
                }
            }
            camera.setOnClickListener {
                dialog.dismiss()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    cameraResultLauncher2.launch(cameraIntent)
                } else {
                    managePermissions.checkPermissions()

                }
            }
            cont_find.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }


    }


    companion object {
        val TAG = AddFundFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): AddFundFragment {
            val fragment = AddFundFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
