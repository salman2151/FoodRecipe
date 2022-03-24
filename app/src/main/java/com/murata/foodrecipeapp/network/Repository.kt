package com.murata.foodrecipeapp.network

import com.murata.foodrecipeapp.network.response.GetTagListResponse
import com.murata.foodrecipeapp.network.response.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Repository {

    @GET("recipes/list")
    suspend fun searchRecipesByTagList(
        @Query("size") size: Int = 20,
        @Query("from") from: Int = 0,
        @Query("tags") tag: String? = null
    ): Response<RecipeListResponse>

    @GET("tags/list")
    suspend fun getTagsList(): Response<GetTagListResponse>

    @GET("recipes/list-similarities")
    suspend fun getListSimilarityByRecipeId(
        @Query("recipe_id") recipeId: Int = -1
    ): Response<RecipeListResponse>
}