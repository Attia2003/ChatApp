package com.example.chatapptest.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.chatapptest.R
import com.example.chatapptest.ui.home.MainActivity
import com.example.chatapptest.ui.login.LoginActivity
import com.example.chatapptest.ui.register.RegisterActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    val viewmodel : SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
                viewmodel.Navigate()
        },3000)

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewmodel.event.observe(this){
            when(it){
                SplashViewEvent.NavigateToHome ->{
                        GoToHome()
        }
                SplashViewEvent.NavigateTologin ->{
                    startLogiactvity()
                }
            }
        }
    }

    private fun startLogiactvity(){

            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    private fun GoToHome(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    }
