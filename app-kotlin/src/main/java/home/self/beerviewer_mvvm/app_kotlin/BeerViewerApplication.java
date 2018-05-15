package home.self.beerviewer_mvvm.app_kotlin;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class BeerViewerApplication extends Application {

//    @Inject
//    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static BeerViewerApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initMemoryLeakDetection();
    }

//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return DaggerAppComponent.builder().application(this).build();
//    }

    private void initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return;

        LeakCanary.install(this);
    }

    public static BeerViewerApplication getInstance() {
        return instance;
    }

}
