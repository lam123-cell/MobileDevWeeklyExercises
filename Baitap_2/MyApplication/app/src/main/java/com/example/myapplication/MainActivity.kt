package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        resultTextView = findViewById(R.id.resultTextView)

        val checkButton: Button = findViewById(R.id.checkButton)
        checkButton.setOnClickListener {
            checkAge()
        }
    }

    private fun checkAge() {
        val name = nameEditText.text.toString()
        val ageString = ageEditText.text.toString()

        if (name.isEmpty() || ageString.isEmpty()) {
            resultTextView.text = "Vui lòng nhập đầy đủ thông tin."
            return
        }

        val age = ageString.toInt()
        val result = when {
            age > 65 -> "$name là người già."
            age in 6..65 -> "$name là người lớn."
            age in 2..5 -> "$name là trẻ em."
            else -> "$name là em bé."
        }

        resultTextView.text = result
    }
}