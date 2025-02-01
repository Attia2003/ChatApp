package com.example.chatapptest.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.ui.Eror.ViewEror
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    val viewLiveEror = MutableLiveData<ViewEror>()

    //liveDataForText
    val UserName = MutableLiveData<String>()
    val UserEmail = MutableLiveData<String>()
    val UserPassword = MutableLiveData<String>()
    val UserPasswordConfirm = MutableLiveData<String>()

    //LiveDataForTextEror
    val UserNameEror = MutableLiveData<String?>()
    val UserEmailEror = MutableLiveData<String?>()
    val UserPasswordEror = MutableLiveData<String?>()
    val UserPasswordConfirmEror = MutableLiveData<String?>()


    val auth = FirebaseAuth.getInstance()

    fun registerUser() {
        if (!ValidForm())
            return
        isLoading.value = true
        auth.createUserWithEmailAndPassword(UserEmail.value!!, UserPassword.value!!)
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


    fun ValidForm(): Boolean {
        var isValid = true
        if (UserName.value.isNullOrEmpty()) {
            UserNameEror.postValue("Enter User Name")
            var isValid = false
        } else {
            UserNameEror.postValue(null)
        }
        if (UserEmail.value.isNullOrEmpty()) {
            UserEmailEror.postValue("Enter ur UserEmail")
            var isValid = false
        } else {
            UserEmailEror.postValue(null)
        }
        if (UserPassword.value.isNullOrEmpty()) {
            UserPasswordEror.postValue("Enter ur UserPassword")
            var isValid = false
        } else {
            UserPasswordEror.postValue(null)
        }
        if (UserPasswordConfirm.value.isNullOrEmpty() ||
            UserPasswordConfirm.value != UserPassword.value
        ) {
            UserPasswordConfirmEror.postValue("Password doesnt Match")
            var isValid = false
        } else {
            UserPasswordConfirmEror.postValue(null)
        }
        return isValid

    }
}


