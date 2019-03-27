package com.edsusantoo.bismillah.belajarroom.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query(
        "UPDATE users SET " +
                "username :username, " +
                "email :email, " +
                "password :password, " +
                "no_telp :notelp, " +
                "alamat :alamat " +
                "WHERE id :id"
    )
    fun update(id: Int, username: String, email: String, password: String, notelp: String, alamat: String)

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUser(id: Int)

}