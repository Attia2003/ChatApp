package com.example.chatapptest.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.domain.usecase.auth.LoginUseCase
import com.example.chatapptest.ui.Error.ViewEror
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    val isLoading = MutableLiveData<Boolean>(false)
    val MessageLiveData = MutableLiveData<ViewEror>()
    
    val UserEmailEror = MutableLiveData<String?>()
    val UserPasswpordEror = MutableLiveData<String?>()
    
    val UserEmail = MutableLiveData<String>("test1@gmail.com")
    val UserPassword = MutableLiveData<String>("123456")
    
    val events = MutableLiveData<LoginViewEvent>()
    
    fun LoginUser() {
        if (!isValidate()) return
        
        isLoading.value = true
        viewModelScope.launch {
            val result = loginUseCase(
                email = UserEmail.value ?: "",
                password = UserPassword.value ?: ""
            )
            
            isLoading.value = false
            
            if (result.isSuccess) {
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
                val error = result.exceptionOrNull()
                MessageLiveData.postValue(
                    ViewEror(message = error?.localizedMessage ?: "Login failed")
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
    
    fun navigateToRegister() {
        events.postValue(LoginViewEvent.NavigateToRegister)
    }
}
