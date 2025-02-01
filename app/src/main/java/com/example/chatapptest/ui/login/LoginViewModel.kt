package com.example.chatapptest.ui.login

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.ui.Eror.ViewEror
import com.example.chatapptest.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel:ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val viewLiveEror = MutableLiveData<ViewEror>()

    //LiveDataErorUser
    val UserNameEror = MutableLiveData<String?>()
    val UserPasswpordEror = MutableLiveData<String?>()


    //liveDataUser
    val UserName = MutableLiveData<String>()
    val UserPassword = MutableLiveData<String>()

    val auth = FirebaseAuth.getInstance()

    fun LoginUser() {
        if (!isValidate())
            return
        isLoading.value = true
        auth.createUserWithEmailAndPassword(UserName.value!!, UserPassword.value!!)
            .addOnCompleteListener {
                isLoading.value = false
                if (it.isSuccessful) {
                    ViewEror(
                        message = it.result.user?.uid
                    )
                } else {
                    //Show Eror
                    viewLiveEror.postValue(
                        ViewEror(
                            message = it.exception?.localizedMessage
                        )
                    )
                }

            }
    }

    fun isValidate(): Boolean {
        var isValid = true

        if (UserName.value.isNullOrEmpty()) {
            UserNameEror.postValue("Enter User Name")
            isValid = false
        } else {
            UserNameEror.postValue(null)
        }
        if (UserPassword.value.isNullOrEmpty()) {
            UserPasswpordEror.postValue("Enter User Password")
            isValid = false
        } else {
            UserPasswpordEror.postValue(null)

        }

        return isValid

    }

}