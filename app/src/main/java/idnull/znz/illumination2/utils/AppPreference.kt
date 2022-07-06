package idnull.znz.illumination2.utils

import android.content.Context
import android.content.SharedPreferences


object AppPreference {

    private const val INIT_USER = "initUser"
    private const val NAME = "preference"
    private lateinit var mPreferences: SharedPreferences

    fun getPreference(context: Context):SharedPreferences{
        mPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        return mPreferences
    }

    fun setInitUser(init:Boolean){
        mPreferences.edit()
            .putBoolean(INIT_USER,init)
            .apply()
    }
    fun getInitUser():Boolean{
        return mPreferences.getBoolean(INIT_USER,false)


    }


}