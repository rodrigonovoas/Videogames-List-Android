package app.rodrigonovoa.myvideogameslist.network

import app.rodrigonovoa.myvideogameslist.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitProvier {
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit!!
    }

    fun getClient(): ApiService{
        return retrofit!!.create(ApiService::class.java)
    }
}