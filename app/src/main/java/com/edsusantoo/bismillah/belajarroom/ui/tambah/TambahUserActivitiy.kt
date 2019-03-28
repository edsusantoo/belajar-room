package com.edsusantoo.bismillah.belajarroom.ui.tambah

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.BelajarRoomDB
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tambah_user.*

class TambahUserActivitiy : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {

    private var database: BelajarRoomDB? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_user)
        setupDB()

        swipe_refresh.isRefreshing = false
        swipe_refresh.setOnRefreshListener(this)

        img_simpan.setOnClickListener {
            tambahUser(
                User(
                    edt_username.text.toString().trim(),
                    edt_email.text.toString().trim(),
                    edt_password.text.toString().trim(),
                    edt_notelp.text.toString().trim(),
                    edt_alamat.text.toString().trim()
                )
            )
        }
    }

    fun setupDB() {
        database = BelajarRoomDB.getInstance(this)
    }

    fun tambahUser(user: User) {
        swipe_refresh.isRefreshing = true
        compositeDisposable.add(Observable.fromCallable { database?.userDao()?.insert(user) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                swipe_refresh.isRefreshing = false
                finish()
            }, { error ->
                swipe_refresh.isRefreshing = false
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            }, {
                swipe_refresh.isRefreshing = false
            })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        BelajarRoomDB.destroyInstance()
        compositeDisposable.dispose()
    }

    override fun onRefresh() {

    }
}
