package com.edsusantoo.bismillah.belajarroom.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        setDataIntent()

        img_edit.setOnClickListener {

        }

    }

    fun setDataIntent() {
        if (intent.extras != null) {
            val dataUser: User = intent?.extras?.getSerializable("datauser") as User
            edt_username.setText(dataUser.username)
            edt_email.setText(dataUser.email)
            edt_password.setText(dataUser.password)
            edt_notelp.setText(dataUser.notelp)
            edt_alamat.setText(dataUser.alamat)
        }
    }
}
