package com.edsusantoo.bismillah.belajarroom.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter (private val userList: MutableList<User>) :
RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ListUserViewHolder {
        return ListUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListUserViewHolder, position: Int) {
        holder.txtUsername.text= userList[position].username
        holder.txtEmail.text=userList[position].email
        holder.txtNoTelp.text=userList[position].notelp
        holder.txtAlamat.text=userList[position].alamat
    }

    inner class ListUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUsername=view.txt_username
        val txtEmail=view.txt_email
        val txtNoTelp=view.txt_notelp
        val txtAlamat=view.txt_alamat
    }
}