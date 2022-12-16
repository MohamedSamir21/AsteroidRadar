package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<Asteroid>)

    @Update
    suspend fun update(asteroids: List<Asteroid>){
        deleteAllAsteroids()
        insertAll(asteroids)
    }

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate <=:date ORDER BY date(closeApproachDate) ASC ")
    fun getTodayAsteroids(date: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM Asteroid ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM Asteroid")
    fun deleteAllAsteroids()
}