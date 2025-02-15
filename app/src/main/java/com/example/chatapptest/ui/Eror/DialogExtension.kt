package com.example.chatapptest.ui.Eror

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showmessage(message :String
                         , posActionName:String?=null
                         , posAction: DialogInterface.OnClickListener?=null,
                         negActionName:String?=null,
                         negAction: DialogInterface.OnClickListener?=null
): AlertDialog {
    val builder  = AlertDialog.Builder(context!!)
    builder.setMessage(message)
    if (posActionName!=null){
        builder.setPositiveButton(posActionName,posAction)
    }
    if (negAction!=null){
        builder.setNegativeButton(negActionName,negAction)
    }
    return  builder.show()
}
fun Activity.showmessage(message :String
                         , posActionName:String?=null
                         , posAction: onActionClickDialog?=null,
                         negActionName:String?=null,
                         negAction: onActionClickDialog?=null,
                         isCancelable:Boolean = true
): AlertDialog {
    val builder  = AlertDialog.Builder(this)
    builder.setMessage(message)
    if (posActionName!=null){
        builder.setPositiveButton(posActionName,
            DialogInterface.OnClickListener{dialog, id ->
                posAction?.onActionclick()
            })
    }
    if (negAction!=null){
        builder.setNegativeButton(negActionName,
            DialogInterface.OnClickListener{dialog, id ->
                negAction.onActionclick()
            })
    }

    builder.setCancelable(isCancelable)
    return  builder.show()
}


