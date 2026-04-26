package com.example.chatapptest.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("Binder")
fun  BindingErorTextInputLayout(
    textInputLayout: TextInputLayout,
    messageEror:String?){
    textInputLayout.error = messageEror?:""

}
