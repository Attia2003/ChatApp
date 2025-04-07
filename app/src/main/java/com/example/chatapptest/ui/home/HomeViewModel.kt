package com.example.chatapptest.ui.home
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.firestore.RoomDao
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.ui.Eror.ViewEror
import com.example.chatapptest.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    val event = SingleLiveEvent<HomeViewEvent>()
    val messageLiveData = SingleLiveEvent<ViewEror>()
    val roomsLiveData = MutableLiveData<List<RommData>>()

    fun GoToAddRoom(){
        Log.d("HomeViewModelstart", "FloatingActionButton clicked!")
            event.postValue(HomeViewEvent.NavigateToAddRoom)

    }
    fun loadrooms(){
        RoomDao.getALLRoomms{
          if (!it.isSuccessful){

              return@getALLRoomms

          }
            val rooms = it.result.toObjects(RommData::class.java)
            roomsLiveData.postValue(rooms)


        }
    }

    fun Logout(){

        messageLiveData.postValue(
            ViewEror(
                message = " are u sure u want log out ",
                psoActionName = "OK",
                posActionClick = {
                    FirebaseAuth.getInstance().signOut()
                    SessionProvider.user = null
                    event.postValue(HomeViewEvent.NavigateToLogin)
                },
                negActionName = "Cancel",

            )
        )



    }

}