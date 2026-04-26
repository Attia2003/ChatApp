package com.example.chatapptest.data.session

import android.content.SharedPreferences
import com.example.chatapptest.database.model.UserData
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val gson = Gson()
    
    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()
    
    init {
        // Load user from shared preferences on initialization
        loadUser()
    }
    
    fun saveUser(user: UserData) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit()
            .putString(KEY_USER, userJson)
            .apply()
        _currentUser.value = user
    }
    
    fun clearUser() {
        sharedPreferences.edit()
            .remove(KEY_USER)
            .apply()
        _currentUser.value = null
    }
    
    private fun loadUser() {
        val userJson = sharedPreferences.getString(KEY_USER, null)
        _currentUser.value = if (userJson != null) {
            gson.fromJson(userJson, UserData::class.java)
        } else {
            null
        }
    }
    
    fun isLoggedIn(): Boolean = _currentUser.value != null
    
    companion object {
        private const val KEY_USER = "current_user"
    }
}
