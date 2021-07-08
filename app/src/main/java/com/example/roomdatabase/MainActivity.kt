package com.example.roomdatabase

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.adapter.rvAdapter
import com.example.roomdatabase.db.UserDb
import com.example.roomdatabase.entity.User
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

var listUser:MutableList<User> = ArrayList()
class MainActivity : AppCompatActivity(), OnItemClickedListener {

    private lateinit var database: UserDb
    private lateinit var rvadapter: rvAdapter

    var deletePosition = -1
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = UserDb.getInstance(this)
        listUser = database.myDao().getAllUsers() as MutableList<User>
        rvadapter = rvAdapter(listUser, this)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = rvadapter


        btn_Add.setOnClickListener {
            if (edt_username.text.isNotEmpty() && edt_country.text.isNotEmpty()) {
                insertUser(edt_username.text.toString(), edt_country.text.toString())
                edt_username.text.clear()
                edt_country.text.clear()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Ma'lumotlarni to'liq kiriting!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        btn_delete.setOnClickListener {
            deleteUser(user, deletePosition)
        }


        btn_updating.setOnClickListener {
        updateUser(user,deletePosition)




        }
    }

    fun insertUser(name: String, country: String) {
        var entityuser = User(0, name, country)
        database.myDao().insertUser(entityuser)
        listUser.add(entityuser)
        notifyChanges()
    }

    fun deleteUser(user: User, position: Int) {

        try {
            if (deletePosition != -1) {
                database.myDao().deleteUser(user)
                listUser.removeAt(position)
                notifyChanges()
            }
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
        }


    }

    fun updateUser(userUpdate:User, position: Int){
        try {
            if (deletePosition != -1){

                val customLayout = layoutInflater.inflate(R.layout.update_item,null)
                val builder =  AlertDialog.Builder(this)
                    .setView(customLayout)
                    .setCancelable(false)

                val alert = builder.create()
                val btnClose = customLayout.findViewById<Button>(R.id.btnUpdateClose)
                btnClose.setOnClickListener {
                    alert.dismiss()
                }
                val edtUsername = customLayout.findViewById<EditText>(R.id.updateUsername)
                val edtCountry = customLayout.findViewById<EditText>(R.id.updateCountry)
                val btnUpdate = customLayout.findViewById<Button>(R.id.btnUpdate)

                Log.d("TAG", "updateUser: ${listUser[position].country}")
                 edtUsername.setText(listUser[position].username)
                 edtCountry.setText(listUser[position].country)

                btnUpdate.setOnClickListener {
                    if (edtUsername.text.isNotEmpty() && edtCountry.text.isNotEmpty()){
                        listUser[position] = User(0,edtUsername.text.toString(),edtCountry.text.toString())
                        database.myDao().updateUser(listUser[position])

                        Log.d("TAG", "updateUser_before: ${edtUsername.text.toString()}")
                        notifyChanges()
                        Toast.makeText(applicationContext, "Tabriklaymiz!!!", Toast.LENGTH_SHORT).show()
                        alert.dismiss()
                    }else{
                        Toast.makeText(applicationContext, "Ma'lumotlar to'liq kirtilmadi!!!", Toast.LENGTH_SHORT).show()
                    }
                }


                alert.show()
            }


        }catch (ex:Exception){
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun notifyChanges() {
        rvadapter = rvAdapter(listUser,this)
        rvadapter.notifyDataSetChanged()
        recyclerView.adapter = rvadapter
    }

    override fun onItemClicked(position: Int) {
        Toast.makeText(applicationContext, listUser[position].username, Toast.LENGTH_SHORT).show()
        user = listUser[position]
        deletePosition = position

    }

}