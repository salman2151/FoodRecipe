package com.murata.foodrecipeapp.network.response

import com.google.gson.annotations.SerializedName
import com.murata.foodrecipeapp.network.dto.DtoRecipe

class RecipeListResponse {

    @SerializedName("count")
    var code: Int = 0

    @SerializedName("results")
    var arrayListDtoRecipe: ArrayList<DtoRecipe>? = null
}