package home.self.beerviewer_mvvm.app_kotlin.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.BaseViewModelFactory
import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepository
import home.self.beerviewer_mvvm.app_kotlin.data.source.BeerRepositoryApi
import home.self.beerviewer_mvvm.app_kotlin.data.source.local.BeerLocalDataSource
import home.self.beerviewer_mvvm.app_kotlin.data.source.remote.BeerRemoteDataSource
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.App
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Local
import home.self.beerviewer_mvvm.app_kotlin.di.qualifier.Remote
import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannel
import home.self.beerviewer_mvvm.app_kotlin.network.appchannel.AppChannelApi
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.SchedulerProvider
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationModule.ProvideModule::class))
internal interface ApplicationModule {

    @Module
    class ProvideModule {

        @Singleton
        @Provides
        fun provideApplicationContext(): Application = BeerViewerApplication.instance

        @Provides
        @Singleton
        fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
    }

    @Singleton
    @App
    @Binds
    fun bindsApplicationRepositoryModule(beerRepository: BeerRepository): BeerRepositoryApi

    @Singleton
    @Remote
    @Binds
    fun bindsBeerRemoteDataSource(beerRemoteRepository: BeerRemoteDataSource): BeerRepositoryApi

    @Singleton
    @Local
    @Binds
    fun bindsBeerLocalDataSource(beerLocalRepository: BeerLocalDataSource): BeerRepositoryApi

    @Binds
    @Singleton
    fun bindsAppChannel(appChannel: AppChannel): AppChannelApi

    @Binds
    fun bindsViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory
}
