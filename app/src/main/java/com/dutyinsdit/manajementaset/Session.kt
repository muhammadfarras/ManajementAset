package com.dutyinsdit.manajementaset

import android.content.Context
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/*
 * Created by farras on 8/7/2020
*/class Session (val context: Context,
                 val currentUser: FirebaseAuth? = null,
                 val authDatas: MutableMap<String,String> = mutableMapOf<String,String>()
) {
    companion object {
        val KEY_SESSION = "auth"

        val KEY_EMAIL = "getEmail"
        val KEY_USER_NAME = "getUserName"
        val KEY_PHONE_NUMBER = "getPhoneNumber"
        val KEY_URL_PHOTO = "getUrlPhoto"
    }

    fun saveAccount () {
        val preference = PreferenceManager.getDefaultSharedPreferences(context).edit()
        val getEmail = currentUser?.currentUser?.email
        val getUserName = currentUser?.currentUser?.displayName
        val getPhoneNumber = currentUser?.currentUser?.phoneNumber
        val getUrlPhoto = currentUser?.currentUser?.photoUrl



        // simpan data di session
        preference.putString(KEY_EMAIL,getEmail)
        preference.putString(KEY_USER_NAME,getUserName)
        preference.putString(KEY_PHONE_NUMBER,getPhoneNumber)
        preference.putString(KEY_URL_PHOTO,getUrlPhoto.toString())
        preference.apply()
    }

    fun removeAccount () {
        val preference = PreferenceManager.getDefaultSharedPreferences(context).edit()
        preference.remove(KEY_SESSION)
        preference.apply()
    }

    fun isLoged ():Boolean {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceCollections = preference.all

        for (data in preferenceCollections){
            authDatas.put(data.key,data.value.toString())
        }
        val isNull = authDatas.isEmpty()
        return  isNull
    }
}