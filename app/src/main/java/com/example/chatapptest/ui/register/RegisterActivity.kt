package com.example.chatapptest.ui.register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptest.R
import com.example.chatapptest.databinding.ActivityRegisterBinding
import com.example.chatapptest.ui.Eror.showmessage

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
        viewmodel.viewLiveEror.observe(this){
            showmessage(it.message?:"something went wrong"
            , posActionName = "try again",
                posAction = {dialog,i->
                    dialog.dismiss()
                }
                )
        }
    }

    fun initview(){
        viewbinding = DataBindingUtil.setContentView(this,
            R.layout.activity_register)
        viewmodel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewbinding?.vm = viewmodel
        viewbinding?.lifecycleOwner = this
    }

}