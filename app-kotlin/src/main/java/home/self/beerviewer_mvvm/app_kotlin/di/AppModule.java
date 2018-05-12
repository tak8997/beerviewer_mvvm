package home.self.beerviewer_mvvm.app_kotlin.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import home.self.beerviewer_mvvm.rx.schedulers.BaseSchedulerProvider;
import home.self.beerviewer_mvvm.rx.schedulers.SchedulerProvider;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}
