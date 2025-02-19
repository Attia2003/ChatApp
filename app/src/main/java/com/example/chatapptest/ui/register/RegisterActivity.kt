package com.example.chatapptest.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptest.ui.home.MainActivity
import com.example.chatapptest.R
import com.example.chatapptest.databinding.ActivityRegisterBinding
import com.example.chatapptest.ui.Eror.showmessage
import com.example.chatapptest.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var viewbinding : ActivityRegisterBinding? = null
    lateinit var viewmodel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        initview()
        subscribeToLiveData()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


    }

    private fun subscribeToLiveData() {
        viewmodel.MessageLiveData.observe(this){
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

    fun initview(){
        viewbinding = DataBindingUtil.setContentView(this,
            R.layout.activity_register)
        viewmodel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewbinding?.vm = viewmodel
        viewbinding?.lifecycleOwner = this
    }

}