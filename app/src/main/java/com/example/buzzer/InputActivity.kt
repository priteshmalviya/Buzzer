package com.example.buzzer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class InputActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val submitbtn=findViewById<Button>(R.id.submitbtn)
        val inputname=findViewById<EditText>(R.id.name)

        submitbtn.setOnClickListener {
            if(inputname.text.toString()=="admin"){
                startActivity(Intent(this,AdminVerificationActivity::class.java))
            }else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", inputname.text.toString())
                startActivity(intent)
            }
            inputname.setText("")
        }
    }
}