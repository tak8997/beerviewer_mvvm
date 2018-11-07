package home.self.beerviewer_mvvm.app_kotlin.di

import dagger.Binds
import dagger.Module
import home.self.beerviewer_mvvm.app_kotlin.base.HttpInteractor
import home.self.beerviewer_mvvm.app_kotlin.base.HttpInteractorApi
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationRepositoryModule.ProvideModule::class))
internal interface HttpInteractorModule {
    @Module
    class ProvideModule

    @Binds
    @Singleton
    fun provideHttpInteractor(httpInteractor: HttpInteractor): HttpInteractorApi
}
