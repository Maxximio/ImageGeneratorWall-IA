package com.example.imagegenerator.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.imagegenerator.R

class InicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, GeneradorActivity::class.java))
            finish()
        }, 3000) // 3 segundos
    }

}

