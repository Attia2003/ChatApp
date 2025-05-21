package com.example.chatapptest.ui.chatpage

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.chatapptest.R
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.databinding.ActivityChatBinding
import com.example.chatapptest.ui.Constant

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initview()
        initparams()
        subscribeToLiveData()

    }

    fun initview(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat)
        binding.vm = viewModel
        binding.lifecycleOwner = this

    }
    fun initparams(){

       val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           intent.getParcelableExtra(Constant.EXTRA_ROOM, RommData::class.java)
       } else {
           intent.getParcelableExtra(Constant.EXTRA_ROOM,) as RommData?
       }
        viewModel.room = room
    }

    fun subscribeToLiveData(){
        viewModel.ToastLiveData.observe(this){
          Toast.makeText(this,it, Toast.LENGTH_SHORT).show()

        }
    }


}