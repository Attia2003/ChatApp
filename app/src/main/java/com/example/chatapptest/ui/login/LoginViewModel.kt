package com.example.chatapptest.ui.login

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.firestore.FireStoreUserDao
import com.example.chatapptest.database.firestore.FireStoreUserDao.getusercollectiion
import com.example.chatapptest.database.model.UserData
import com.example.chatapptest.ui.Eror.ViewEror
import com.example.chatapptest.ui.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel:ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val MessageLiveData = MutableLiveData<ViewEror>()

    //LiveDataErorUser
    val UserEmailEror = MutableLiveData<String?>()
    val UserPasswpordEror = MutableLiveData<String?>()


    //liveDataUser
    val UserEmail = MutableLiveData<String>()
    val UserPassword = MutableLiveData<String>()

    val events = MutableLiveData<LoginViewEvent>()

    val auth = FirebaseAuth.getInstance()

    fun LoginUser() {
        if (!isValidate())

            return
                isLoading.value = true
                auth.createUserWithEmailAndPassword(
                    UserEmail.value!!, UserPassword.value!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            GetUserFromFireStore(it.result.user?.uid)
                        } else {
                            MessageLiveData.postValue(
                                ViewEror(message = it.exception?.localizedMessage))
                        }
                    }
            }

    fun isValidate(): Boolean {
        var isValid = true

        if (UserEmail.value.isNullOrEmpty()) {
            UserEmailEror.postValue("Enter User Name")
            isValid = false
        } else {
            UserEmailEror.postValue(null)
        }
        if (UserPassword.value.isNullOrEmpty()) {
            UserPasswpordEror.postValue("Enter User Password")
            isValid = false
        } else {
            UserPasswpordEror.postValue(null)

        }

        return isValid

    }

    fun GetUserFromFireStore(uid:String?){
    FireStoreUserDao.getuserbyid(uid){
        isLoading.value = false
        if (it.isSuccessful){
            val user = it.result.toObject(UserData::class.java)
            SessionProvider.user = user
            MessageLiveData.postValue(
                ViewEror(
                    message = "Login Success",
                    psoActionName = "okee",
                    posActionClick ={
                        events.postValue(LoginViewEvent.NavigateToHome)

                    },isCancelable = false)
            )



        }else{
            isLoading.value = false
            MessageLiveData.postValue(
                ViewEror(message = it.exception?.localizedMessage))
        }
    }
    }

    fun navigateToRegister(){
        events.postValue(LoginViewEvent.NavigateToRegister)
    }







}

