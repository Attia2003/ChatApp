 package com.example.chatapptest.ui.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptest.R
import com.example.chatapptest.databinding.ActivityLoginBinding
import com.example.chatapptest.ui.Eror.showmessage

 class LoginActivity : AppCompatActivity() {
     lateinit var viewbinding: ActivityLoginBinding
     lateinit var viewmodel: LoginViewModel
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
         viewmodel.viewLiveEror.observe(this) {
             showmessage(it.message ?: "something went wrong", posActionName = "try again",
                 posAction = { dialog, i ->
                     dialog.dismiss()
                 })
         }
     }
         fun initView() {
             viewbinding = DataBindingUtil.setContentView(
                 this,
                 R.layout.activity_login
             )
             viewmodel = ViewModelProvider(this)[LoginViewModel::class.java]
             viewbinding.vmlogin = viewmodel
             viewbinding.lifecycleOwner = this

         }
     }
