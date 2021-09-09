package com.idong.core.di

import com.idong.core.BuildConfig
import com.idong.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ridhopratama on 30,August,2021
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val hostname = "api.ridhopratama.online"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/QBg6Qb6zhiRM1+llI+cDY45OyLSStQXv32OR277mYIU=")
            .build()

        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient) : ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_DOTA2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}