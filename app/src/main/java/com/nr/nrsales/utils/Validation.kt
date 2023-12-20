package com.nr.nrsales.utils

import android.util.Patterns
import android.widget.EditText

class Validation {
    companion object{
    fun getEmailValidCheck(edtEmail: EditText): Boolean {
        return if (edtEmail.text.toString() == "") {
            edtEmail.error = "Email Can't be Empty"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Email Can't be Empty")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()) {
            edtEmail.error = "Invalid Email Address"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Invalid Email Address")
            false
        } else {
            edtEmail.clearFocus()
            edtEmail.error = null
            true
        }
    }

    fun getPhoneValidCheck(edtEmail: EditText): Boolean {
        return if (edtEmail.text.toString() == "") {
            edtEmail.error = "Phone Can't be Empty"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Phone Can't be Empty")
            false
        } else if (!Patterns.PHONE.matcher(edtEmail.text).matches()) {
            edtEmail.error = "Invalid Phone Number"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Invalid Phone Number")
            false
        } else {
            edtEmail.clearFocus()
            edtEmail.error = null
            true
        }
    }

    fun getEmailPhoneValidCheck(edtEmail: EditText): Boolean {
        return if (edtEmail.text.toString() == "") {
            edtEmail.error = "Email/Phone Can't be Empty"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Email/Phone Can't be Empty")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()
            && !Patterns.PHONE.matcher(edtEmail.text).matches()
        ) {
            edtEmail.error = "Invalid Email/Phone"
            edtEmail.requestFocus()
            GlobalUtility.showToast("Invalid Email/Phone Details")
            false
        } else {
            edtEmail.clearFocus()
            edtEmail.error = null
            true
        }
    }

    fun getPassValidCheck(edtPass: EditText): Boolean {
        return if (edtPass.text.toString() == "") {
            edtPass.error = "Password Can't be Empty"
            GlobalUtility.showToast("Password Can't be Empty")

            edtPass.requestFocus()
            false
        } else if (edtPass.text.length <= 5) {
            edtPass.error = "Password Can't be Smaller than 6 latter's"
            GlobalUtility.showToast("Password Can't be Smaller than 6 latter's")
            edtPass.requestFocus()
            false
        } else {
            edtPass.clearFocus()
            edtPass.error = null
            true
        }
    }

    fun getNormalValidCheck(edtPass: EditText): Boolean {
        return if (edtPass.text.toString() == "") {
            edtPass.error = "Field Can't be Empty"
            //GlobalUtility.showToast("Password Can't be Smaller than 6 latter's")
            edtPass.requestFocus()
            false
        } else {
            edtPass.clearFocus()
            edtPass.error = null
            true
        }
    }
        fun getStringEmptyCheck(car_image_path: String  ,message :String): Boolean {
        return if (car_image_path.toString() == "") {
            GlobalUtility.showToast(message)
            false
        } else {
           true
        }
    }
}
}