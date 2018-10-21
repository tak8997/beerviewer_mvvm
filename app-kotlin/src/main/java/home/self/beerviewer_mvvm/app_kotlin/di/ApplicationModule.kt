package home.self.beerviewer_mvvm.app_kotlin.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModelFactory
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerDao
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerLocalDataSource
import home.self.beerviewer_mvvm.app_kotlin.data.source.remote.BeerRemoteDataSource
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Local
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Remote
import home.self.beerviewer_mvvm.app_kotlin.network.BeerApiService
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationModule.ProvideModule::class))
internal interface ApplicationModule {

    @Module
    class ProvideModule {

        @Singleton
        @Provides
        fun provideContext(application: Application): Context = application

        @Provides
        @Singleton
        fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
    }

//    @Singleton
//    @Remote
//    @Provides
//    fun provideBeerRemoteDataSource(beerApiService: BeerApiService): BeerRepositoryApi {
//        return BeerRemoteDataSource(beerApiService)
//    }
//
//    @Singleton
//    @Local
//    @Provides
//    fun provideBeerLocalDataSource(beerDao: BeerDao): BeerRepositoryApi {
//        return BeerLocalDataSource(beerDao)
//    }

    @Singleton
    @Binds
    fun bindApplicationRepositoryModule(beerRepository: BeerRepository): BeerRepositoryApi

    @Binds
    fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory
}
