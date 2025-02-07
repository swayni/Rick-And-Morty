package sw.swayni.basemvvm.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sw.swayni.basemvvm.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().readTimeout(1200, TimeUnit.SECONDS).connectTimeout(1200,TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun apiCreator(client: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_API_STRING).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(client).build()

}