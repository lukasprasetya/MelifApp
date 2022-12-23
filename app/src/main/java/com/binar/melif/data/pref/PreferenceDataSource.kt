package com.binar.melif.data.pref

import android.util.Log


interface PreferenceDataSource {
    fun isSkipIntro(): Boolean
    fun setSkipIntro(isSkipIntro: Boolean)
}

class PreferenceDataSourceImpl(private val preference: Preference) :
    PreferenceDataSource {

    override fun isSkipIntro(): Boolean {
        Log.d("datamsauk", preference.isSkipIntro().toString())
        return preference.isSkipIntro()
    }

    override fun setSkipIntro(isSkipIntro: Boolean) {
        Log.d("datamsauklog", isSkipIntro.toString())
        return preference.setSkipIntro(isSkipIntro)
    }

}