package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.udacity.database.AsteroidsDatabaseDao

class MainViewModel (
        val database: AsteroidsDatabaseDao,
        application: Application) : AndroidViewModel(application){


}