package com.example.buzzer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminActivity : AppCompatActivity() {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var adapter: UserAdapter

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        mDbRef = FirebaseDatabase.getInstance().reference

        val userList=ArrayList<String>()
        val rcv=findViewById<RecyclerView>(R.id.rcv)
        val swich=findViewById<Switch>(R.id.switch1)

        mDbRef.child("answers").addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()
                    for (postSnapshot in snapshot.children) {
                        val messege = postSnapshot.getValue(String::class.java)
                        userList.add(messege!!)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {}
        })

        mDbRef.child("users").addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                var num=0
                for (postSnapshot in snapshot.children) {
                    num++
                }
                findViewById<TextView>(R.id.userNum).text="Active User : ${num.toString()}"
            }
            override fun onCancelled(error: DatabaseError) {}
        })


        swich.setOnClickListener{
            mDbRef.child("canAnswer").setValue(swich.isChecked.toString())
            if(!swich.isChecked){
                mDbRef.child("answers").removeValue()
            }
        }

        adapter = UserAdapter(userList)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter

    }

    override fun onPause() {
        super.onPause()
        mDbRef.child("canAnswer").setValue("false")
        mDbRef.child("answers").removeValue()
    }

}