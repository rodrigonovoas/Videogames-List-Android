package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private var retrofit: Retrofit? = null

    // TODO: use moshi instead of gson
    fun getRetrofit(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit!!
    }

    fun getClient(): ApiService{
        return retrofit!!.create(ApiService::class.java)
    }
}