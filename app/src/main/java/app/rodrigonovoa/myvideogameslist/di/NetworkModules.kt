package app.rodrigonovoa.myvideogameslist.di

import app.rodrigonovoa.myvideogameslist.network.ApiRepository
import app.rodrigonovoa.myvideogameslist.network.ApiService
import app.rodrigonovoa.myvideogameslist.network.RetrofitClient
import org.koin.dsl.module
import retrofit2.Retrofit

val netWorkModules = module {
    single { retrofitProvider() }
    single { clientProvider(get()) }
    single { ApiRepository(get())}
}

private fun retrofitProvider(): Retrofit {
    return RetrofitClient().getRetrofit()
}

private fun clientProvider(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}