package home.self.beerviewer_mvvm.app_kotlin.di

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import home.self.beerviewer_mvvm.app_kotlin.rx.schedulers.BaseSchedulerProvider

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}
