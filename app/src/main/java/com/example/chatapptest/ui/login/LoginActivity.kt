 package com.example.chatapptest.ui.login

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
import com.example.chatapptest.databinding.ActivityLoginBinding
import com.example.chatapptest.ui.Error.showmessage
import com.example.chatapptest.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class LoginActivity : AppCompatActivity() {
     lateinit var viewbinding: ActivityLoginBinding
     private val viewmodel: LoginViewModel by viewModels()
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         enableEdgeToEdge()
         initView()
         subscribeToLiveData()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }
     }

     fun subscribeToLiveData() {
         viewmodel.MessageLiveData.observe(this) {
             showmessage(it.message ?: "something went wrong",
                 posActionName = it.psoActionName,
                 posAction = it.posActionClick,
                 negActionName = it.negActionName,
                 negAction = it.negActionClick,
                 isCancelable = it.isCancelable)
         }
         viewmodel.events.observe(this){
             when(it){
                 LoginViewEvent.NavigateToHome->{
                     navigateToHome()
                 }
                 LoginViewEvent.NavigateToRegister->{
                     navigateToRegister()
                 }
             }
         }
     }

     private fun navigateToRegister() {
         val intent = Intent(this, RegisterActivity::class.java)
         startActivity(intent)
         finish()
     }

     private fun navigateToHome() {
         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
         finish()
     }

     fun initView() {
             viewbinding = DataBindingUtil.setContentView(
                 this,
                 R.layout.activity_login
             )
             viewbinding.vmlogin = viewmodel
             viewbinding.lifecycleOwner = this

         }
     }
