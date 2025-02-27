package com.example.chatapptest.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptest.R
import com.example.chatapptest.databinding.ActivityMainBinding
import com.example.chatapptest.ui.room.AddRoomActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initview()
        subscribeToLiveData()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    fun subscribeToLiveData(){

        viewModel.event.observe(this){
            when(it){
                HomeViewEvent.NavigateToAddRoom->{
                    OpenAddRoomActivty()
                }
            }


        }
    }


    fun OpenAddRoomActivty(){
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)

    }

    fun initview(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        binding.vm = viewModel
        binding.lifecycleOwner = this

    }
}