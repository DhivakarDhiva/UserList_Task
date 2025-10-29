package com.example.task.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.R
import com.example.task.data.model.User

class UserAdapter(val context: Context,private val users: List<User>,val onDeleteClick : (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list_item, parent, false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        with(holder) {
            val user = users[position]
            name.text = user.name
            email.text = user.email
            company.text = user.company
            address.text = user.address
            phn_num.text = user.phone
            Glide.with(context)
                .load(user.photo)
                .into(user_img)

            del_btn_img.setOnClickListener {

                onDeleteClick(user)


            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.user_name_txt)
        val email: TextView = view.findViewById(R.id.user_email_txt)
        val company: TextView = view.findViewById(R.id.user_company_txt)
        val address: TextView = view.findViewById(R.id.user_addr_txt)
        val phn_num: TextView = view.findViewById(R.id.user_phn_txt)
        val user_img: ImageView = view.findViewById(R.id.user_image)
        val del_btn_img: ImageView = view.findViewById(R.id.delete_btn_img)
    }
}