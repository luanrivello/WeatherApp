package com.example.findinglogs.viewmodel;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.repo.Repository;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final int FETCH_INTERVAL = 120_000;
    private final Repository mRepository;
    private final MutableLiveData<List<WeatherUiInfo>> _weatherList = new MutableLiveData<>(new ArrayList<>());
    private final LiveData<List<WeatherUiInfo>> weatherList = _weatherList;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable fetchRunnable = this::startFetching;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        startFetching();
    }

    public LiveData<List<WeatherUiInfo>> getWeatherList() {
        return weatherList;
    }

    public void refreshForecasts(){
        handler.removeCallbacks(fetchRunnable);
        fetchAllForecasts();
        handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
    }

    private void startFetching() {
        fetchAllForecasts();
        handler.postDelayed(fetchRunnable, FETCH_INTERVAL);
    }

    private void fetchAllForecasts() {
        if (Logger.ISLOGABLE) Logger.d(TAG, "fetchAllForecasts()");
        HashMap<String, String> localizations = mRepository.getLocalizations();
        List<WeatherUiInfo> updatedList = new ArrayList<>();

        for (String latlon : localizations.values()) {
            mRepository.retrieveForecast(latlon, new WeatherCallback() {
                @Override
                public void onSuccess(Weather result) {
                    updatedList.add(new WeatherUiInfo(result, getApplication()));
                    if (updatedList.size() == localizations.size()) {
                        _weatherList.setValue(updatedList);
                    }
                }

                @Override
                public void onFailure(String error) {
                    Log.d(TAG, error);
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        handler.removeCallbacks(fetchRunnable);
        super.onCleared();
    }

    public void retrieveForecast(String latLon, WeatherCallback callback) {
        mRepository.retrieveForecast(latLon, callback);
    }
}