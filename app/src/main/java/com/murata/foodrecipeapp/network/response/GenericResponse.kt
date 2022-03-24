package com.murata.foodrecipeapp.network.response

class GenericResponse<T>(
    var apiStatus: Int = -1,
    var code: Int = -1,
    var data: ArrayList<T>? = null
) {
    companion object {
        const val LOADING = 0
        const val SUCCESS = 1
        const val ERROR = 2

    }
}