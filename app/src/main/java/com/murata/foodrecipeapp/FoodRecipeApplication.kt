package com.murata.foodrecipeapp

import android.app.Application
import com.murata.foodrecipeapp.network.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FoodRecipeApplication : Application() {
    companion object {

        private fun getClient(): OkHttpClient {/*= OkHttpClient.Builder().hostnameVerifier { _, _ -> true }.build();*/
            val interceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request()
                    val newRequest: Request.Builder = request
                        .newBuilder()
                        .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
                        .addHeader(
                            "X-RapidAPI-Key",
                            "81c2176afdmsh8e0bb47ef90cd4ap1100a8jsnc3655d5a8f05"
                        )
                    return chain.proceed(newRequest.build())
                }
            }
//            interceptor.level = HttpLoggingInterceptor.Level.BODY


            return OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).hostnameVerifier { _, _ -> true }.build()
        }

        private var repository: Repository =
            Retrofit.Builder().baseUrl("https://tasty.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create()) // map server keys on serialized keys in class
                .client(getClient())
                .build()
                .create(Repository::class.java)

        fun getRepository(): Repository {

            return repository
        }

        fun main(work: suspend (() -> Unit)) = CoroutineScope(Dispatchers.Main).launch { work() }
    }
}