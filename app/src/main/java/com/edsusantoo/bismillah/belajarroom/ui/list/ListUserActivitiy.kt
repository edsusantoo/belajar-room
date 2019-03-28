package com.edsusantoo.bismillah.belajarroom.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.BelajarRoomDB
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_user.*

class ListUserActivitiy : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var database: BelajarRoomDB? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        setupDB()
        setupRecycler()
        swipe_refresh.isRefreshing = false
        swipe_refresh.setOnRefreshListener(this)
        getListUser()
    }

    private fun setupDB() {
        database = BelajarRoomDB.getInstance(this)
    }

    private fun setupRecycler() {
        recycler_user.layoutManager = LinearLayoutManager(this)
    }

    private fun getListUser() {
        swipe_refresh.isRefreshing = true
        compositeDisposable.add(database!!.userDao().getAll()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    swipe_refresh.isRefreshing = false
                    setupDataRecycler(it)
                },
                { error ->
                    swipe_refresh.isRefreshing = false
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                },
                {
                    swipe_refresh.isRefreshing = false
                }
            )
        )
    }

    private fun setupDataRecycler(data: MutableList<User>) {
        val listUserAdapter = ListUserAdapter(data)
        recycler_user.adapter = listUserAdapter
        listUserAdapter.notifyDataSetChanged()

    }

    override fun onDestroy() {
        super.onDestroy()
        BelajarRoomDB.destroyInstance()
        compositeDisposable.dispose()
    }

    override fun onRefresh() {
        getListUser()
    }

}
