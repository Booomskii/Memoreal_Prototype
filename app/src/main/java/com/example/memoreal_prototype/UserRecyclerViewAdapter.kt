package com.example.memoreal_prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoreal_prototype.db.User

class UserRecyclerViewAdapter:RecyclerView.Adapter<UserViewHolder>() {

    private val userList = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    fun setList(users: List<User>){
        userList.clear()
        userList.addAll(users)
    }
}

class UserViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(user: User){
        val userName = view.findViewById<TextView>(R.id.tvUsername)
        val userEmail = view.findViewById<TextView>(R.id.tvEmail)
        val userPassword = view.findViewById<TextView>(R.id.tvPassword)
        val userFirstName = view.findViewById<TextView>(R.id.tvFirstName)
        val userLastName = view.findViewById<TextView>(R.id.tvLastName)
        val userMI = view.findViewById<TextView>(R.id.tvMI)
        val userContactNum = view.findViewById<TextView>(R.id.tvContactNum)
        val userBirthDate = view.findViewById<TextView>(R.id.tvBirthDate)
        val userPicture = view.findViewById<TextView>(R.id.tvPicture)
        userName.text = user.username
        userEmail.text = user.email
        userPassword.text = user.password
        userFirstName.text = user.firstName
        userLastName.text = user.lastName
        userMI.text = user.mi
        userContactNum.text = user.contactNumber.toString()
        userBirthDate.text = user.birthDate.toString()
        userPicture.text = user.picture
    }
}