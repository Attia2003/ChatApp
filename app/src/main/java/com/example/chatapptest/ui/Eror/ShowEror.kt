package com.example.chatapptest.ui.Eror

data class ViewEror(
  val message:String?=null,
    val throwable: Throwable?=null,
      val ontrytagainclicklistner: ontruagainclicklistner?=null

  )

 fun interface ontruagainclicklistner{
        fun ontryagainclick()
}

        