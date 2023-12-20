package com.nr.nrsales.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent
import android.content.pm.PackageManager;
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

class ManagePermissions(val activity: Activity, private val  list: List<String>, private val code:Int) {

    // Check permissions at runtime
    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showAlert()
        } else {
         //   Toast.makeText(activity, "Permissions already granted.", Toast.LENGTH_SHORT).show()
        }
    }


    // Check permissions status
    private fun isPermissionsGranted(): Int {
        // PERMISSION_GRANTED : Constant Value: 0
        // PERMISSION_DENIED : Constant Value: -1
        var counter = 0;
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }


    // Find the first denied permission
    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED) return permission
        }
        return ""
    }


    // Show alert dialog to request permissions
    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need permission")
        builder.setMessage("Some permissions are required to do the task.")
        builder.setPositiveButton("OK") { dialog, which -> requestPermissions() }
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }
    fun openAppSettings(activity: Activity) {
        val intent = Intent()

        // For Android 10 and above, open the specific app settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
        } else {
            // For versions less than Android 10, open the general app settings
            intent.action = Settings.ACTION_APPLICATION_SETTINGS
        }

        activity.startActivity(intent)
    }

    // Request the permissions at run time
    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Show an explanation asynchronously
            openAppSettings(activity)
            Toast.makeText(activity, "Should show an explanation.", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        }
    }


    // Process permissions result
    fun processPermissionsResult(requestCode: Int, permissions: Array<String>,
                                 grantResults: IntArray): Boolean {
        var result = 0
        if (grantResults.isNotEmpty()) {
            for (item in grantResults) {
                result += item
            }
        }
        if (result == PackageManager.PERMISSION_GRANTED) return true
        return false
    }
}