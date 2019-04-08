package com.edsusantoo.bismillah.belajarroom.ui.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsusantoo.bismillah.belajarroom.R
import com.edsusantoo.bismillah.belajarroom.data.local.db.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(
    private val userList: MutableList<User>,
    private val selectedUserList: MutableList<User>?,
    private val listener: OnListUserListener
) :
    RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ListUserViewHolder {
        return ListUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListUserViewHolder, position: Int) {
        holder.bind(listener)
        holder.txtUsername.text = userList[position].username
        holder.txtEmail.text = userList[position].email
        holder.txtNoTelp.text = userList[position].notelp
        holder.txtAlamat.text = userList[position].alamat

        if (selectedUserList != null) {
            if (selectedUserList.contains(userList[position])) {
                //selected
                holder.linearItemUser.setBackgroundColor(Color.parseColor("#6000574B"))
                holder.cardListUser.setCardBackgroundColor(Color.parseColor("#6000574B"))
            } else {
                holder.cardListUser.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.linearItemUser.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }


    }

    inner class ListUserViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener,
        View.OnLongClickListener {
        val txtUsername = view.txt_username
        val txtEmail = view.txt_email
        val txtNoTelp = view.txt_notelp
        val txtAlamat = view.txt_alamat
        val cardListUser = view.card_item_user
        val linearItemUser = view.linear_item_user

        private var listener: OnListUserListener? = null

        override fun onClick(v: View?) {
            listener?.onItemClick(userList[adapterPosition], v, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            listener?.onItemLongClick(userList[adapterPosition], v, adapterPosition)
            return true
        }

        fun bind(listener: OnListUserListener) {
            this.listener = listener
            cardListUser.setOnClickListener(this)
            cardListUser.setOnLongClickListener(this)
        }
    }

    interface OnListUserListener {
        fun onItemClick(user: User, view: View?, position: Int)
        fun onItemLongClick(user: User, view: View?, position: Int)
    }
}