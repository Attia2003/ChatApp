package com.example.chatapptest.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.domain.usecase.auth.RegisterUseCase
import com.example.chatapptest.ui.Error.ViewEror
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>(false)
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

    fun registerUser() {
        if (!isValidForm()) return

        isLoading.value = true
        viewModelScope.launch {
            // Parse userName to firstName and lastName
            val nameParts = (userName.value ?: "").split(" ", limit = 2)
            val firstName = nameParts.getOrNull(0) ?: ""
            val lastName = nameParts.getOrNull(1) ?: ""
            
            val result = registerUseCase(
                email = userEmail.value ?: "",
                password = userPassword.value ?: "",
                firstName = firstName,
                lastName = lastName
            )

            isLoading.value = false

            if (result.isSuccess) {
                messageLiveData.postValue(
                    ViewEror(
                        message = "Registration Success",
                        psoActionName = "OK",
                        posActionClick = {
                            events.postValue(ResgisterViewEvent.NavigateToHome)
                        },
                        isCancelable = false
                    )
                )
            } else {
                val error = result.exceptionOrNull()
                messageLiveData.postValue(
                    ViewEror(message = error?.localizedMessage ?: "Registration failed")
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

    fun navigateToLogin() {
        events.postValue(ResgisterViewEvent.NavigatetoLogin)
    }
}
