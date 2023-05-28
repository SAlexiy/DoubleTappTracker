package com.salexey.shared_preference.preference

import android.content.Context

class SharedPreferencesStorage(context: Context){

    private val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)

    fun setValue(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}
