package com.murata.foodrecipeapp.network.dto

import java.io.Serializable

data class DtoRecipe(
    val id: Int,
    val video_url: String?,
    val description: String?,
    val name: String,
    val thumbnail_url: String?
) : Serializable