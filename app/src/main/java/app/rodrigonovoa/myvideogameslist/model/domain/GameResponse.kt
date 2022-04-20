package app.rodrigonovoa.myvideogameslist.model.domain

import java.io.Serializable

data class GameResponse(val id:Int, val name:String, val released: String, val metacritic: Int,
                        val background_image:String){

}