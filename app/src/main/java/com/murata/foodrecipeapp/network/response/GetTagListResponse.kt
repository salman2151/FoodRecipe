package com.murata.foodrecipeapp.network.response

import com.murata.foodrecipeapp.network.dto.DtoTag
import com.google.gson.annotations.SerializedName

class GetTagListResponse {

    @SerializedName("count")
    var code: Int = 0

    @SerializedName("results")
    var arrayListDtoTags: ArrayList<DtoTag>? = null
}