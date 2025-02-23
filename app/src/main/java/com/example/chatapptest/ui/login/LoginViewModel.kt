package com.example.chatapptest.ui.login

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.SessionProvider

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

    val UserEmailEror = MutableLiveData<String?>()
    val UserPasswpordEror = MutableLiveData<String?>()

    val UserEmail = MutableLiveData<String>("test1@gmail.com")
    val UserPassword = MutableLiveData<String>("123456")

    val events = MutableLiveData<LoginViewEvent>()

    private val auth = FirebaseAuth.getInstance()

    fun LoginUser() {
        if (!isValidate()) return

        isLoading.value = true
        auth.signInWithEmailAndPassword(UserEmail.value!!, UserPassword.value!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uid = it.result.user?.uid
                    GetUserFromFireStore(uid)
                } else {
                    isLoading.value = false
                    MessageLiveData.postValue(
                        ViewEror(message = it.exception?.localizedMessage)
                    )
                }
            }
    }

    private fun isValidate(): Boolean {
        var isValid = true

        if (UserEmail.value.isNullOrEmpty()) {
            UserEmailEror.postValue("Enter User Email")
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

    private fun GetUserFromFireStore(uid: String?) {
        if (uid == null) {
            isLoading.value = false
            MessageLiveData.postValue(ViewEror(message = "User ID is null"))
            return
        }

        viewModelScope.launch {
            try {
                val userSnapshot = FireStoreUserDao.getuserbyid(uid)
                isLoading.value = false
                if (userSnapshot != null && userSnapshot.exists()) {
                    val user = userSnapshot.toObject(UserData::class.java)
                    SessionProvider.user = user
                    MessageLiveData.postValue(
                        ViewEror(
                            message = "Login Success",
                            psoActionName = "OK",
                            posActionClick = {
                                events.postValue(LoginViewEvent.NavigateToHome)
                            },
                            isCancelable = false
                        )
                    )
                } else {
                    MessageLiveData.postValue(ViewEror(message = "User not found"))
                }
            } catch (e: Exception) {
                isLoading.value = false
                MessageLiveData.postValue(ViewEror(message = e.localizedMessage))
            }
        }
    }

    fun navigateToRegister() {
        events.postValue(LoginViewEvent.NavigateToRegister)
    }


}

