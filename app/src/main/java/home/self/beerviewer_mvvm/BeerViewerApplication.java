package home.self.beerviewer_mvvm;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;

public class BeerViewerApplication extends Application {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static BeerViewerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initMemoryLeakDetection();
    }

    private void initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return;

        LeakCanary.install(this);
    }

//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return DaggerAppComponent.builder().application(this).build();
//    }

    public static BeerViewerApplication getInstance() {
        return instance;
    }

}
