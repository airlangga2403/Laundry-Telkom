package org.d3ifcool.laundrytelkom.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3ifcool.laundrytelkom.model.ListLaundry
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

enum class ApiStatus { LOADING, SUCCESS, FAILED}

object MapApiLaundry {
    private const val BASE_URL = "https://mocki.io/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }

    interface ApiService {
        @GET("685cc0e7-1d34-48da-a70a-3ac7f4050941")
        suspend fun getData(): ListLaundry
    }
}