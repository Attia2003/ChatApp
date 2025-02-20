package com.example.chatapptest.ui.home
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.util.SingleLiveEvent

class HomeViewModel : ViewModel() {

    val event = SingleLiveEvent<HomeViewEvent>()

    fun GoToAddRoom(){
        Log.d("HomeViewModelstart", "FloatingActionButton clicked!")
            event.postValue(HomeViewEvent.NavigateToAddRoom)

    }

}