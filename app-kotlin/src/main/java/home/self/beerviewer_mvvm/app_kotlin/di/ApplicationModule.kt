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
import home.self.beerviewer_mvvm.app_kotlin.data.source.remote.BeerRemoteDataSource
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Remote
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
//
//    @Singleton
//    @Local
//    @Provides
//    fun provideBeerLocalDataSource(beerDao: BeerDao): BeerRepositoryApi {
//        return BeerLocalDataSource(beerDao)
//    }

    @Singleton
    @App
    @Binds
    fun bindsApplicationRepositoryModule(beerRepository: BeerRepository): BeerRepositoryApi

    @Singleton
    @Remote
    @Binds
    fun bindsBeerRemoteDataSource(beerRemoteRepository: BeerRemoteDataSource): BeerRepositoryApi

    @Binds
    fun bindsViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory
}
