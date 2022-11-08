package com.example.buzzer

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {


    private lateinit var mDbRef : DatabaseReference
    private lateinit var name:String

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbRef = FirebaseDatabase.getInstance().reference
        name=intent.getStringExtra("name").toString()

        val button=findViewById<Button>(R.id.btn)

        mDbRef.child("canAnswer").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val isactiv = snapshot.getValue(String::class.java)
                        button.isEnabled = isactiv=="true"
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })

        button.setOnClickListener {
            button.isEnabled=false
            //val current = LocalDateTime.now()
            mDbRef.child("answers").push().setValue(name)
        }
    }


    /*override fun onBackPressed() {
        super.onBackPressed()
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra("name",name)
        startActivity(intent)
    }*/

    override fun onPause() {
        super.onPause()
        mDbRef.child("users").child(name).removeValue()
        Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mDbRef.child("users").child(name).setValue(name)
    }
}