package com.nr.nrsales.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nr.nrsales.R
import com.nr.nrsales.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPrf @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val USER = "USER"
    fun getStoredTag(str: String): String {
        return prefs.getString(str, "")!!
    }

    fun setStoredTag(str: String, query: String) {
        prefs.edit().putString(str, query).apply()
    }

    fun getUser(): User {
        return try {
            val gson = Gson()
            val json: String? = prefs.getString(USER, "")
            val obj: User = gson.fromJson(json, User::class.java)
            obj
        } catch (e: Exception) {
            User("", "")
        }
    }

    fun setUser(query: User) {
        val gson = Gson()
        val json = gson.toJson(query)
        prefs.edit().putString(USER, json).apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    companion object {
        const val LOGIN: String = "login"
        const val USER_ID: String = "user_id"
    }

}