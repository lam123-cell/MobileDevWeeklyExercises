package com.example.myapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val profileImage = findViewById<ImageView>(R.id.profile_image)
        val fullName = findViewById<TextView>(R.id.full_name)
        val address = findViewById<TextView>(R.id.address)
        val backButton = findViewById<ImageButton>(R.id.button_back)
        val editButton = findViewById<ImageButton>(R.id.button_edit)

        // Đặt dữ liệu vào các view
        fullName.text = "Nguyen Nhat Lam"
        address.text = "Phu Yen, VIET NAM"
        profileImage.setImageResource(R.drawable.my_profile_image)

        // Xử lý sự kiện click cho nút Back
        backButton.setOnClickListener {

            finish()
        }

        editButton.setOnClickListener {

            // ...
        }
    }
}