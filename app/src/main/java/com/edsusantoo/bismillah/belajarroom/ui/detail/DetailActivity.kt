package com.edsusantoo.bismillah.belajarroom.ui.detail

import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var database: BelajarRoomDB? = null
    private val compositeDisposable = CompositeDisposable()

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        swipe_refresh.setOnRefreshListener(this)

        setupDB()
        setDataIntent()

        img_edit.setOnClickListener {
            setEnableEdit()
        }

        img_simpan.setOnClickListener {
            editUser(
                edt_username.text.toString().trim(),
                edt_email.text.toString().trim(),
                edt_password.text.toString().trim(),
                edt_notelp.text.toString().trim(),
                edt_alamat.text.toString().trim()
            )
        }

    }

    fun setupDB() {
        database = BelajarRoomDB.getInstance(this)
    }

    private fun setDataIntent() {
        if (intent.extras != null) {
            val dataUser: User = intent?.extras?.getSerializable("datauser") as User
            id = dataUser.id
            edt_username.setText(dataUser.username)
            edt_email.setText(dataUser.email)
            edt_password.setText(dataUser.password)
            edt_notelp.setText(dataUser.notelp)
            edt_alamat.setText(dataUser.alamat)
        }
    }

    private fun setEnableEdit() {
        edt_username.isEnabled = true
        edt_username.isFocusableInTouchMode = true
        edt_username.isFocusable = true

        edt_email.isEnabled = true
        edt_email.isFocusableInTouchMode = true
        edt_email.isFocusable = true

        edt_password.isEnabled = true
        edt_password.isFocusableInTouchMode = true
        edt_password.isFocusable = true

        edt_notelp.isEnabled = true
        edt_notelp.isFocusableInTouchMode = true
        edt_notelp.isFocusable = true

        edt_alamat.isEnabled = true
        edt_alamat.isFocusableInTouchMode = true
        edt_alamat.isFocusable = true

        img_simpan.visibility = View.VISIBLE
        img_edit.visibility=View.GONE
    }

    private fun editUser(username: String, email: String, password: String, notelp: String, alamat: String) {
        swipe_refresh.isRefreshing = true
        compositeDisposable.add(Observable.fromCallable {
            database?.userDao()?.update(id, username, email, password, notelp, alamat)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    swipe_refresh.isRefreshing = false
                    img_edit.visibility=View.VISIBLE
                    finish()
                }, { error ->
                    swipe_refresh.isRefreshing = false
                    img_edit.visibility=View.VISIBLE
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                }, {
                    swipe_refresh.isRefreshing = false
                    img_edit.visibility=View.VISIBLE
                })
        )
    }

    override fun onRefresh() {

    }


}
