package com.edsusantoo.bismillah.belajarroom.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edsusantoo.bismillah.belajarroom.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
