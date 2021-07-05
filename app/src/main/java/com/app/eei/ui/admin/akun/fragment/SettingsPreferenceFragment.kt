package com.app.eei.ui.admin.akun.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.app.eei.R


class SettingsPreferenceFragment: PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var switchPreferenceCompat: SwitchPreferenceCompat
    private lateinit var SWITCH: String
    private lateinit var LOCALIZATION: String

    companion object{
        const val TIME="09:00"
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_admin)// masukan xml preferences

    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

}


