package com.example.chatapptest.database.model

import com.example.chatapptest.R

data class CategoryData(
    val id: Int,
    val name: String,
    val imagesres : Int
){
    companion object {
        fun getCatogries() = listOf<CategoryData>(
            CategoryData(1, "Sports", R.drawable.sports),
            CategoryData(2, "music", R.drawable.music),
            CategoryData(3, "Movie", R.drawable.movie),
            CategoryData(4, "Sports", R.drawable.game)

        )

        fun getcatogriesbyid(id: Int?) :Int{
              return  when(id){
                    1 ->  R.drawable.sports
                    2 ->  R.drawable.music
                    3 ->  R.drawable.movie
                    4 ->  R.drawable.game
                  else ->
                      R.drawable.sports

                }
        }

    }


}
