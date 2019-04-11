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
            setEnableEditText()
        }

    }

    private fun setDataIntent() {
        if (intent.extras != null) {
            val dataUser: User = intent?.extras?.getSerializable("datauser") as User
            edt_username.setText(dataUser.username)
            edt_email.setText(dataUser.email)
            edt_password.setText(dataUser.password)
            edt_notelp.setText(dataUser.notelp)
            edt_alamat.setText(dataUser.alamat)
        }
    }

    private fun setEnableEditText() {
        edt_username.isEnabled = true
        edt_username.isFocusableInTouchMode=true
        edt_username.isFocusable=true

        edt_email.isEnabled = true
        edt_email.isFocusableInTouchMode=true
        edt_email.isFocusable=true

        edt_password.isEnabled = true
        edt_password.isFocusableInTouchMode=true
        edt_password.isFocusable=true

        edt_notelp.isEnabled = true
        edt_notelp.isFocusableInTouchMode=true
        edt_notelp.isFocusable=true

        edt_alamat.isEnabled = true
        edt_alamat.isFocusableInTouchMode=true
        edt_alamat.isFocusable=true
    }
}
