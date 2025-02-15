package com.svg.dogsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.svg.dogsapp.domain.model.DogImageEntity

@Database(entities = [DogImageEntity::class], version = 1, exportSchema = false)
abstract class DogDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        @Volatile
        private var INSTANCE: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "dog_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}