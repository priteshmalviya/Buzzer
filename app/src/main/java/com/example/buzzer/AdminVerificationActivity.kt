package com.example.buzzer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

class AdminVerificationActivity : AppCompatActivity() {

    private lateinit var mDbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_verification)

        mDbRef = FirebaseDatabase.getInstance().reference
        val btn = findViewById<Button>(R.id.passsubmitbtn)
        val passInput = findViewById<EditText>(R.id.passtext)
        var passOnServer=""

        mDbRef.child("pass").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    passOnServer = snapshot.getValue(String::class.java).toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        btn.setOnClickListener {
            if (passOnServer == passInput.text.toString()) {
                startActivity(Intent(baseContext, AdminActivity::class.java))
                Toast.makeText(baseContext, "Logged In As Admin.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Wrong Password", Toast.LENGTH_SHORT).show()
            }
            passInput.setText("")
        }

    }
}