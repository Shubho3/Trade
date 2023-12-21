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
import com.nr.nrsales.R
import com.nr.nrsales.databinding.FragmentEditProfileBinding
import com.nr.nrsales.ui.LaunchingActivity
import com.nr.nrsales.ui.fragments.login_signup.LoginFragment
import com.nr.nrsales.utils.BaseFragment
import com.nr.nrsales.utils.GlobalUtility
import com.nr.nrsales.utils.ManagePermissions
import com.nr.nrsales.utils.NetworkResult
import com.nr.nrsales.utils.SharedPrf
import com.nr.nrsales.utils.Validation
import com.nr.nrsales.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    lateinit var mBinding: FragmentEditProfileBinding
    private lateinit var context: Context
    private lateinit var sharedPrf: SharedPrf
    private var aadhar_front: String = ""
    private var aadhar_back: String = ""
    private var pan_front: String = ""
    private var pan_back: String = ""
    private val REQUEST_CODE = 123
    private lateinit var managePermissions: ManagePermissions
    private lateinit var list: List<String>
    private val viewmodel by viewModels<ProfileViewModel>()
    private fun getProfile() {
        GlobalUtility.showProgressMessage(requireActivity(), requireActivity().getString(R.string.loading))
        val map: HashMap<String, Any> = HashMap()
        map["user_id"] = sharedPrf.getStoredTag(SharedPrf.USER_ID)
        viewmodel.fetchGetUserResponse(map)
        viewmodel.getUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    GlobalUtility.hideProgressMessage()
                    response.data?.let {
                        if (it.status == "1") {
                            mBinding.user = it.result
                            //   mBinding.aadharFront.load(it.result.aadhar_front) { crossfade(true) }
                            //   mBinding.aadharBack.load(it.result.aadhar_back) { crossfade(true) }
                            //    mBinding.panFront.load(it.result.pan_front) { crossfade(true) }
                            //    mBinding.panBack.load(it.result.pan_back) { crossfade(true) }
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
        mBinding = binding as FragmentEditProfileBinding
        context = requireActivity()
        sharedPrf = SharedPrf(requireContext())
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
        managePermissions = ManagePermissions(
            requireActivity(), list, REQUEST_CODE
        )
        managePermissions.checkPermissions()
        getProfile()

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
            mBinding.aadharFront.load(bitmap) { crossfade(true) }
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
                            mBinding.aadharFront.setImageBitmap(bitmap)
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
                mBinding.aadharFront.setImageBitmap(bitmap)
            }
        }
    }
    private var cameraResultLauncher3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
            aadhar_back = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
            Log.e("ImagePath", "onActivityResult: $aadhar_back")
            mBinding.aadharBack.setImageBitmap(bitmap)
        }
    }
    private var galleryResultLauncher3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                            aadhar_back = tempfile.absolutePath
                            Log.e("ImagePath", "onActivityResult: $aadhar_back")
                            mBinding.aadharBack.setImageBitmap(bitmap)
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
                aadhar_back = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
                Log.e("ImagePath", "onActivityResult: $aadhar_back")
                mBinding.aadharBack.setImageBitmap(bitmap)
            }
        }
    }
    private var cameraResultLauncher4 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
            pan_front = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
            Log.e("ImagePath", "onActivityResult: $pan_front")
            mBinding.panFront.setImageBitmap(bitmap)
        }
    }
    private var galleryResultLauncher4 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                            pan_front = tempfile.absolutePath
                            Log.e("ImagePath", "onActivityResult: $pan_front")
                            mBinding.panFront.setImageBitmap(bitmap)
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
                pan_front = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
                Log.e("ImagePath", "onActivityResult: $pan_front")
                mBinding.panFront.setImageBitmap(bitmap)
            }
        }
    }
    private var cameraResultLauncher45 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
            pan_back = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
            Log.e("ImagePath", "onActivityResult: $pan_back")
            mBinding.panBack.setImageBitmap(bitmap)
        }
    }
    private var galleryResultLauncher45 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                            pan_back = tempfile.absolutePath
                            Log.e("ImagePath", "onActivityResult: $pan_back")
                            mBinding.panBack.setImageBitmap(bitmap)
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
                pan_back = "" + GlobalUtility.saveToInternalStorage(bitmap, requireActivity())
                Log.e("ImagePath", "onActivityResult: $pan_back")
                mBinding.panBack.setImageBitmap(bitmap)
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
        mBinding.headerLay.tvLogo.text = "Profile"
        mBinding.headerLay.imgHeader.setOnClickListener { onBackPressed() }
        mBinding.btnUpdate.setOnClickListener {}
        mBinding.aadharFront.setOnClickListener {
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
        mBinding.aadharBack.setOnClickListener {
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
                    galleryResultLauncher3.launch(i)
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
                    cameraResultLauncher3.launch(cameraIntent)
                } else {
                    managePermissions.checkPermissions()

                }
            }
            cont_find.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
        mBinding.panBack.setOnClickListener {
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
                    galleryResultLauncher45.launch(i)
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
                    cameraResultLauncher45.launch(cameraIntent)
                } else {
                    managePermissions.checkPermissions()

                }
            }
            cont_find.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
        mBinding.panFront.setOnClickListener {
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
                    galleryResultLauncher4.launch(i)
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
                    cameraResultLauncher4.launch(cameraIntent)
                } else {
                    managePermissions.checkPermissions()

                }
            }
            cont_find.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
        mBinding.btnLogout.setOnClickListener {
            sharedPrf.clearAll()
            startActivity(Intent(activity, LaunchingActivity::class.java))
            requireActivity().finish()
        }
        mBinding.btnUpdate.setOnClickListener {
            Log.e("TAG", "setOnClickListener: " + mBinding.edtName.text.toString())
            if (!Validation.getNormalValidCheck(mBinding.edtName)) {
                return@setOnClickListener
            } else if (!Validation.getNormalValidCheck(mBinding.edtPhone)) {
                return@setOnClickListener
            } else if (!Validation.getNormalValidCheck(mBinding.edtEmail)) {
                return@setOnClickListener
            } else if (!Validation.getNormalValidCheck(mBinding.edtPass)) {
                return@setOnClickListener
            } else {
                GlobalUtility.showProgressMessage(requireActivity(), "Uploading Data...")
                val bodyx: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                bodyx.addFormDataPart("user_name", mBinding.edtName.text.toString()).addFormDataPart("user_id", sharedPrf.getStoredTag(SharedPrf.USER_ID))
                    .addFormDataPart("mobile", mBinding.edtPhone.text.toString())
                    .addFormDataPart("email", mBinding.edtEmail.text.toString()).addFormDataPart("password", mBinding.edtPass.text.toString()).build()
                if (aadhar_front != "") bodyx.addPart(MultipartBody.Part.createFormData("aadhar_front", "aadhar_front.png", RequestBody.create("image/*".toMediaTypeOrNull(), File(aadhar_front))))
                if (aadhar_back != "") bodyx.addPart(MultipartBody.Part.createFormData("aadhar_back", "aadhar_back.png", RequestBody.create("image/*".toMediaTypeOrNull(), File(aadhar_back))))
                if (pan_front != "") bodyx.addPart(MultipartBody.Part.createFormData("pan_front", "pan_front.png", RequestBody.create("image/*".toMediaTypeOrNull(), File(pan_front))))
                if (pan_back != "") bodyx.addPart(MultipartBody.Part.createFormData("pan_back", "pan_back.png", RequestBody.create("image/*".toMediaTypeOrNull(), File(pan_back))))
                viewmodel.fetchupdateUserResponse(bodyx.build())
            }
            viewmodel.updateUser.observe(this) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        GlobalUtility.hideProgressMessage()
                        response.data?.let {
                            Toast.makeText(context, "User Updated Successfully", Toast.LENGTH_SHORT).show()
                            getProfile()
                        }
                    }

                    is NetworkResult.Error -> {
                        GlobalUtility.hideProgressMessage()
                        Log.e(LoginFragment.TAG, "fetchLoginResponse: " + response.message)
                        getProfile()

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
}
