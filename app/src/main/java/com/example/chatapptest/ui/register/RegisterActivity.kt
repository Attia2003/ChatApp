package com.example.chatapptest.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.chatapptest.ui.home.MainActivity
import com.example.chatapptest.R
import com.example.chatapptest.databinding.ActivityRegisterBinding
import com.example.chatapptest.ui.Error.showmessage
import com.example.chatapptest.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityRegisterBinding
    private val viewmodel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        subscribeToLiveData()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun subscribeToLiveData() {
        viewmodel.messageLiveData.observe(this){
            showmessage(it.message ?: "something went wrong",
                posActionName = "Okee",
                posAction = it.posActionClick,
                negActionName = it.negActionName,
                negAction = it.negActionClick,
                isCancelable = it.isCancelable)
        }
        viewmodel.events.observe(this){
            when(it){
                ResgisterViewEvent.NavigateToHome->{
                        navigateToHome()

                    }
                ResgisterViewEvent.NavigatetoLogin->{
                        navigateToLogin()

                    }
            }

        }
    }

    private fun navigateToLogin() {
      val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun navigateToHome() {
       val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
          finish()
    }

    fun initview() {
        viewbinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewbinding.vm = viewmodel
        viewbinding.lifecycleOwner = this
    }
}
