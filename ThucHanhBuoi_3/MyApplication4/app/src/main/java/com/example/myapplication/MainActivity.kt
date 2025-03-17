package com.example.myapplication

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.graphics.Typeface
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helloTextView: TextView = findViewById(R.id.helloTextView)
        val sayHiButton: Button = findViewById(R.id.sayHiButton)

        sayHiButton.setOnClickListener {
            val fullName = "Nguyen Nhat Lam"
            val spannableString = SpannableString(fullName)
            spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, fullName.length, 0)

            val finalText = SpannableStringBuilder("I'm\n").append(spannableString)
            helloTextView.text = finalText
        }
    }
}