package com.stivenvelasquez.formulario.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stivenvelasquez.formulario.ui.signup.SignUpActivity
import com.stivenvelasquez.formulario.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel= ViewModelProvider(this)[MainViewModel::class.java ]

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navigateToSignUpObserver = Observer<Boolean> { navigateToSignUp ->
            if (navigateToSignUp) {
                startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
                finish()
            }
        }
        mainViewModel.navigateToSignUp.observe(this, navigateToSignUpObserver)
    }
}