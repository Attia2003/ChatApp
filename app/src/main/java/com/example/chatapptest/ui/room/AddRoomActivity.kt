package com.example.chatapptest.ui.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptest.R
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.databinding.ActivityAddRoomBinding
import com.example.chatapptest.ui.Eror.showmessage
import com.example.chatapptest.ui.home.MainActivity

class AddRoomActivity : AppCompatActivity() {
    lateinit var viewModel: AddRoomViewModel
    lateinit var binding: ActivityAddRoomBinding
    lateinit var carogreyadapter : CatogriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this)[AddRoomViewModel::class.java]


        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initview()
        subscribeToLiveData()

    }

   fun initview(){
       carogreyadapter = CatogriesAdapter(viewModel.categories)
       binding.vm = viewModel
       binding.lifecycleOwner = this
       binding.spinerCatogeries.adapter = carogreyadapter

       binding.spinerCatogeries.onItemSelectedListener =
           object : AdapterView.OnItemSelectedListener {
               override fun onItemSelected(
                   parent: AdapterView<*>?,
                   view: View?,
                   position: Int,
                   id: Long
               ) {
                   viewModel.categorySelected(position)
               }

               override fun onNothingSelected(parent: AdapterView<*>?) {

               }
           }


   }

    fun subscribeToLiveData(){
        viewModel.messageLiveData.observe(this){task->
            showmessage(task.message ?: "something went wrong",
                posActionName = task.psoActionName,
                posAction = task.posActionClick,
                negActionName = task.negActionName,
                negAction = task.negActionClick,
                isCancelable = task.isCancelable)

        }
        viewModel.event.observe(this,::handleEvent)

    }
    fun handleEvent(event: AddRoomEvent){
        when(event){
            AddRoomEvent.NavigateToHome->{
               finish()
            }


}
    }

}