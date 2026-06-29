package com.example.findinglogs.model.repo;


import android.app.Application;

import com.example.findinglogs.model.repo.local.SharedPrefManager;
import com.example.findinglogs.model.repo.remote.WeatherManager;
import com.example.findinglogs.model.repo.remote.api.WeatherCallback;
import com.example.findinglogs.model.util.Logger;

import java.util.ArrayList;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private static Repository repository;

    private final WeatherManager weatherManager;
    private final SharedPrefManager sharedPrefManagerManager;
    private final ArrayList<String> localizations = new ArrayList<>();

    public static Repository getInstance(Application application) {
        if (repository == null) {
            repository = new Repository(application);
        }

        return repository;
    }

    private Repository(Application application) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "Repository()");

        weatherManager = new WeatherManager();
        sharedPrefManagerManager = SharedPrefManager.getInstance(application);

        String prefLocations = sharedPrefManagerManager.readString("localizations");
        if (Logger.ISLOGABLE) Logger.d(TAG, "Saved localizations: " + prefLocations);

        if (prefLocations == null) {
            addDefaultLocations();
        } else {
            loadLocationsFrom(prefLocations);
        }
    }

    private void addDefaultLocations() {
        if (Logger.ISLOGABLE) Logger.d(TAG, "addDefaultLocations()");
        localizations.add("-8.05428,-34.8813");
        localizations.add("-9.39416,-40.5096");
        localizations.add("-8.284547,-35.969863");

        sharedPrefManagerManager.writeString("localizations", localizations.toString());
        if (Logger.ISLOGABLE) Logger.d(TAG, "Created new localizations: " + localizations.toString());
    }

    private void loadLocationsFrom(String prefLocations) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "loadLocationsFrom(): " + prefLocations);
        String[] entries = prefLocations.substring(1).split(" ");

        for (String entrie:entries) {
            String latlon = entrie.substring(0, entrie.length()-1);

            localizations.add(latlon);
            if (Logger.ISLOGABLE) Logger.d(TAG, "Loaded: " + latlon);
        }
    }

    public void retrieveForecast(String latLon, WeatherCallback callback) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "retrieveForecast for:" + latLon);
        weatherManager.retrieveForecast(latLon, callback);
    }

    public void saveString(String key, String value) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "saveString()");
        sharedPrefManagerManager.writeString(key, value);
    }

    public String readString(String key) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "readString()");
        return sharedPrefManagerManager.readString(key);
    }

    public ArrayList<String> getLocalizations() {
        return localizations;
    }

    public void addLocalization(String lat, String lon) {
        if (Logger.ISLOGABLE) Logger.d(TAG, "addLocalization(): " + lat + "," + lon);
        String latlon = lat + "," + lon;

        localizations.add(latlon);
        sharedPrefManagerManager.writeString("localizations", localizations.toString());
        if (Logger.ISLOGABLE) Logger.d(TAG, "Saved localizations: " + localizations.toString());
    }

    public void removeLocalization(String lat, String lon) {
        localizations.remove(lat + "," + lon);
        sharedPrefManagerManager.writeString("localizations", localizations.toString());
        if (Logger.ISLOGABLE) Logger.d(TAG, "Saved localizations: " + localizations.toString());
    }
}