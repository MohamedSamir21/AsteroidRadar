package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AsteroidsDatabaseDao {

    @Insert
    suspend fun insert(asteroid: Asteroids)

    @Update
    suspend fun update(asteroid: Asteroids)

    @Query("SELECT * FROM asteroids_table ORDER BY asteroidId DESC")
    fun getAllAsteroids(): LiveData<List<Asteroids>>
}