package com.edsusantoo.bismillah.belajarroom.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edsusantoo.bismillah.belajarroom.data.local.db.dao.UserDao
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User

@Database(entities = [User::class], version = 1)
abstract class BelajarRoomDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: BelajarRoomDB? = null
        fun getInstance(context: Context): BelajarRoomDB? {
            synchronized(BelajarRoomDB::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    BelajarRoomDB::class.java,
                    "belajarroom.db"
                )
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}