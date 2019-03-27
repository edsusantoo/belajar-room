package com.edsusantoo.bismillah.belajarroom.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.ui.list.ListUserActivitiy
import com.edsusantoo.bismillah.belajarroom.ui.tambah.TambahUserActivitiy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_tambah_user.setOnClickListener(this)
        btn_list_user.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_tambah_user -> startActivity(Intent(this, TambahUserActivitiy::class.java))
            R.id.btn_list_user -> startActivity(Intent(this, ListUserActivitiy::class.java))
        }
    }
}
