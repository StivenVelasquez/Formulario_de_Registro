package com.stivenvelasquez.formulario.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import android.content.Intent
import com.stivenvelasquez.formulario.ui.signup.SignUpActivity
import kotlin.concurrent.timerTask
import com.stivenvelasquez.formulario.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        val timer = Timer()
        timer.schedule(
            timerTask {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            },  900
        )

    }

}