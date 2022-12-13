package com.udacity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids_table")
data class Asteroids(
    @PrimaryKey(autoGenerate = true)
    var asteroidId: Long = 0L,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Long = 0L,
    @ColumnInfo(name = "estimated_diameter_max")
    val estimatedDiameterMax: Long = 0L,
    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean
)