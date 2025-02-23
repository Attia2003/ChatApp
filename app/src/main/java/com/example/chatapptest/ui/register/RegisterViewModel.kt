package com.example.chatapptest.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.SessionProvider

import com.example.chatapptest.database.model.UserData
import com.example.chatapptest.ui.Eror.ViewEror
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<ViewEror>()

    // LiveData for input fields
    val userName = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userPasswordConfirm = MutableLiveData<String>()


    val userNameError = MutableLiveData<String?>()
    val userEmailError = MutableLiveData<String?>()
    val userPasswordError = MutableLiveData<String?>()
    val userPasswordConfirmError = MutableLiveData<String?>()

    val events = MutableLiveData<ResgisterViewEvent>()

    private val auth: FirebaseAuth = Firebase.auth

    fun registerUser() {
        if (!isValidForm()) return

        isLoading.value = true
        viewModelScope.launch {
            try {

                val result = auth.createUserWithEmailAndPassword(
                    userEmail.value!!,
                    userPassword.value!!
                ).await()

                val uid = result.user?.uid
                insertInFirestore(uid)
            } catch (e: Exception) {
                isLoading.value = false
                messageLiveData.postValue(
                    ViewEror(message = e.localizedMessage)
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        var isValid = true

        if (userName.value.isNullOrEmpty()) {
            userNameError.postValue("Enter User Name")
            isValid = false
        } else {
            userNameError.postValue(null)
        }

        if (userEmail.value.isNullOrEmpty()) {
            userEmailError.postValue("Enter your Email")
            isValid = false
        } else {
            userEmailError.postValue(null)
        }

        if (userPassword.value.isNullOrEmpty()) {
            userPasswordError.postValue("Enter your Password")
            isValid = false
        } else {
            userPasswordError.postValue(null)
        }

        if (userPasswordConfirm.value.isNullOrEmpty() ||
            userPasswordConfirm.value != userPassword.value
        ) {
            userPasswordConfirmError.postValue("Passwords do not match")
            isValid = false
        } else {
            userPasswordConfirmError.postValue(null)
        }

        return isValid
    }

    private fun insertInFirestore(uid: String?) {
        if (uid == null) {
            isLoading.value = false
            messageLiveData.postValue(ViewEror(message = "User ID is null"))
            return
        }

        val user = UserData(
            id = uid,
            userName = userName.value,
            email = userEmail.value
        )

        viewModelScope.launch {
            try {
                FireStoreUserDao.createuser(user)
                isLoading.value = false
                messageLiveData.postValue(
                    ViewEror(
                        message = "Registration Success",
                        psoActionName = "OK",
                        posActionClick = {
                            SessionProvider.user = user
                            events.postValue(ResgisterViewEvent.NavigateToHome)
                        },
                        isCancelable = false
                    )
                )
            } catch (e: Exception) {
                isLoading.value = false
                messageLiveData.postValue(ViewEror(message = e.localizedMessage))
            }
        }
    }

    fun navigateToLogin() {
        events.postValue(ResgisterViewEvent.NavigatetoLogin)
    }
}
