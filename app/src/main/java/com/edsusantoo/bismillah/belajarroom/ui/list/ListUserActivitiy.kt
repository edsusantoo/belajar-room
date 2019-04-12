package com.edsusantoo.bismillah.belajarroom.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.BelajarRoomDB
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import com.edsusantoo.bismillah.belajarroom.ui.detail.DetailActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_user.*


class ListUserActivitiy : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var database: BelajarRoomDB? = null
    private val compositeDisposable = CompositeDisposable()
    private var selectedUserList: MutableList<User> = mutableListOf()
    private var userList: MutableList<User>? = null
    private var menuContext: Menu? = null
    private var mActionMode: ActionMode? = null
    private var isMultiSelect: Boolean = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        toolbar.setNavigationOnClickListener {
            finish()
        }
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
        recycler_user.itemAnimator = DefaultItemAnimator()
    }

    private fun getListUser() {
        swipe_refresh.isRefreshing = true
        compositeDisposable.add(database!!.userDao().getAll()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    swipe_refresh.isRefreshing = false
                    userList = it.toMutableList()
                    setupDataRecycler(userList!!)
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
        val listUserAdapter = ListUserAdapter(data, selectedUserList, listUserListener)
        recycler_user.adapter = listUserAdapter
        listUserAdapter.notifyDataSetChanged()
        //agarr recycleview tidak kembali keatas saat ada data baru
        //listUserAdapter.notifyItemRangeInserted(listUserAdapter.itemCount, data.size - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        BelajarRoomDB.destroyInstance()
        compositeDisposable.dispose()
    }

    override fun onResume() {
        super.onResume()
        getListUser()
    }

    override fun onRefresh() {
        getListUser()
    }

    private val listUserListener = object : ListUserAdapter.OnListUserListener {
        override fun onItemLongClick(user: User, view: View?, position: Int) {
            when (view?.id) {
                R.id.card_item_user -> {
                    if (!isMultiSelect) {
                        isMultiSelect = true
                        if (mActionMode == null) {
                            mActionMode = startActionMode(mActionModeCallback)
                            this@ListUserActivitiy.user = user

                        }
                    }

                    multi_select(position)
                }

            }
        }

        override fun onItemClick(user: User, view: View?, position: Int) {
            when (view?.id) {
                R.id.card_item_user -> {
                    if (isMultiSelect) {
                        multi_select(position)
                    } else {
                        val i = Intent(this@ListUserActivitiy, DetailActivity::class.java)
                        i.putExtra("datauser", user)
                        startActivity(i)
                    }

                }

            }
        }

    }

    private val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.action_delete -> {
                    showDialogs("Peringatan!", "Apakah anda yakin ingin menghapus ini ?")
                    true
                }
                else -> false
            }
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater: MenuInflater = mode!!.menuInflater
            inflater.inflate(R.menu.user_menu, menu)
            menuContext = menu
            swipe_refresh.isEnabled = false
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            mActionMode = null
            isMultiSelect = false
            selectedUserList.clear()
            setupDataRecycler(userList!!)

            swipe_refresh.isEnabled = true
        }

    }

    private fun deleteUser(user: User) {
        swipe_refresh.isRefreshing = true
        compositeDisposable.add(Observable.fromCallable { database!!.userDao().delete(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    swipe_refresh.isRefreshing = false


                    mActionMode?.finish()
                    isMultiSelect = false
                    selectedUserList.clear()
                    getListUser()
                },
                { error ->
                    swipe_refresh.isRefreshing = false
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                },
                {
                    swipe_refresh.isRefreshing = false

                })
        )
    }

    fun multi_select(position: Int) {
        if (mActionMode != null) {

            if (selectedUserList.contains(userList?.get(position)))
                selectedUserList.remove(userList?.get(position))
            else
                selectedUserList.add(userList!![position])

            if (selectedUserList.size > 0)
                mActionMode?.title = "" + selectedUserList.size
            else
                mActionMode?.title = ""

            setupDataRecycler(userList!!)

        }
    }

    fun showDialogs(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton("Ya") { _, _ ->

            deleteUser(user)

        }

        builder.setNegativeButton(
            "Tidak"
        ) { _, _ ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}
