package sw.swayni.rickandmorty.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import retrofit2.Retrofit
import sw.swayni.rickandmorty.BuildConfig
import sw.swayni.rickandmorty.data.Api
import sw.swayni.rickandmorty.data.local.ILocalDataSource
import sw.swayni.rickandmorty.data.local.LocalDataSource
import sw.swayni.rickandmorty.data.realm_model.FavoriteCharacterModel
import sw.swayni.rickandmorty.data.remote.IRemoteDataSource
import sw.swayni.rickandmorty.data.remote.RemoteDataSource
import sw.swayni.rickandmorty.data.repository.IRepository
import sw.swayni.rickandmorty.data.repository.Repository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRealmConfiguration() = RealmConfiguration.Builder(schema = setOf(
        FavoriteCharacterModel::class))
        .name(BuildConfig.DATABASE_NAME).compactOnLaunch().schemaVersion(1).build()

    @Singleton
    @Provides
    fun provideRealmDatabase(realmConfiguration: RealmConfiguration) = Realm.open(realmConfiguration)


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: Api): IRemoteDataSource = RemoteDataSource(api)

    @Singleton
    @Provides
    fun provideLocalDataSource(realm: Realm): ILocalDataSource = LocalDataSource(realm)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource): IRepository = Repository(remoteDataSource, localDataSource)
}