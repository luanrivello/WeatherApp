package com.example.findinglogs.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findinglogs.model.repo.Repository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application)

    private val _localizations = MutableLiveData<List<Pair<String, String>>>()
    val localizations: LiveData<List<Pair<String, String>>> = _localizations

    init {
        loadLocalizations()
    }

    fun addLocalization(lat: String, lon: String) {
        lat.toDoubleOrNull() ?: return
        lon.toDoubleOrNull() ?: return

        repository.addLocalization(lat, lon)
        loadLocalizations()
    }

    fun removeLocalization(lat: String, lon: String) {
        repository.removeLocalization(lat, lon)
        loadLocalizations()
    }

    private fun loadLocalizations() {
        _localizations.value = repository.localizations.map {
            val parts = it.split(",")
            Pair(parts[0], parts[1])
        }
    }
}