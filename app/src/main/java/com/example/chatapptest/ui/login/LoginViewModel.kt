package com.example.chatapptest.ui.login

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.ui.Eror.ViewEror
import com.example.chatapptest.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        viewModelScope.launch {
            if (checkIfEmailExists(UserName.value!!)) {
                isLoading.value = false
                viewLiveEror.postValue(
                    ViewEror(message = "Email already registered. Please login."))
            } else {

                auth.createUserWithEmailAndPassword(UserName.value!!, UserPassword.value!!)
                    .addOnCompleteListener { task ->
                        isLoading.value = false
                        if (task.isSuccessful) {
                            viewLiveEror.postValue(
                                ViewEror(message = "Registration successful. UID: ${task.result?.user?.uid}"))
                        } else {
                            viewLiveEror.postValue(
                                ViewEror(message = task.exception?.localizedMessage))
                        }
                    }
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

    suspend fun checkIfEmailExists(email: String): Boolean {
        return try {
            val result = FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).await()
            !result.signInMethods.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }
}

