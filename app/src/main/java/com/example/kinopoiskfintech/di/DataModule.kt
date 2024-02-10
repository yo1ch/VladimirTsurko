package com.example.kinopoiskfintech.di


import com.example.kinopoiskfintech.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {
    companion object {
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor { chain ->
                    chain.proceed(

                        chain.request().newBuilder()
                            .header(
                                name = "X-API-KEY",
                                value = API_KEY
                            )
                            .build()
                    )
                }
                .build()
        }
        @Provides
        fun provideMoviesRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


        @Provides
        fun bindReceiptApi(retrofit: Retrofit): MoviesApi =
            retrofit.create(MoviesApi::class.java)


        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

    }
}