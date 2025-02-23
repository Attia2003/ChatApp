package com.example.chatapptest.ui.room

import android.util.Log
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.firestore.RoomDao
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.ui.Eror.ViewEror
import com.example.chatapptest.util.SingleLiveEvent

class AddRoomViewModel: ViewModel()  {
        val event = SingleLiveEvent<AddRoomEvent>()

        val roomName = MutableLiveData<String>()
        val roomDescription = MutableLiveData<String>()
        val roomNameErors = MutableLiveData<String?>()
        val roomDescriptionErors = MutableLiveData<String?>()

        val messageLiveData = MutableLiveData<ViewEror>()

        val categories = CategoryData.getCatogries()
        var selectedCategory = categories[0]
        val isCreatingRoom = MutableLiveData<Boolean>()

        fun createRoom() {
                Log.d("CreateRoom", "Button clicked!")

                if (!Isvalidte()) {
                        Log.e("CreateRoom", "Validation failed!")
                        return
                }

                if (isCreatingRoom.value == true) {
                        Log.e("CreateRoom", "Already creating room!")
                        return
                }

                isCreatingRoom.postValue(true)
                Log.d("CreateRoom", "Validation passed, sending request...")

                RoomDao.creatRoom(
                        decription = roomDescription.value ?: "",
                        adminId = SessionProvider.user?.id ?: "",
                        title = roomName.value ?: "",
                        categoryid = selectedCategory.id
                ) { result ->
                        isCreatingRoom.postValue(false)
                        if (result.isSuccessful) {
                                Log.d("CreateRoom", "Room created successfully!")

                                messageLiveData.postValue(
                                        ViewEror(
                                                message = "Room created successfully!",
                                                psoActionName = "OK",
                                                posActionClick = {
                                                        event.postValue(AddRoomEvent.NavigateToHome)
                                                },
                                                isCancelable = false
                                        )
                                )
                        } else {
                                Log.e("CreateRoom", "Firestore Error: ${result.exception?.localizedMessage}") // ✅ Step 6: Firestore error

                                messageLiveData.postValue(
                                        ViewEror(message = "Error: ${result.exception?.localizedMessage}")
                                )
                        }
                }
        }



        fun Isvalidte():Boolean{
                var isvalidd = true
                if(roomName.value.isNullOrEmpty()){
                        roomNameErors.postValue("Room Name is Required")
                        isvalidd=false
                }else{
                        roomNameErors.postValue(null)
                }
                 if(roomDescription.value.isNullOrEmpty()){
                        roomDescriptionErors.postValue("Room Description is Required")
                         isvalidd=false
                }else{
                        roomDescriptionErors.postValue(null)
                 }
                        return isvalidd
        }

        fun categorySelected(position: Int){
                selectedCategory = categories[position]


        }

}