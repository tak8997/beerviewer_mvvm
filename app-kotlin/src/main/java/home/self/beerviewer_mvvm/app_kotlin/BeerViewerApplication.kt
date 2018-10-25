package home.self.beerviewer_mvvm.app_kotlin

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import home.self.beerviewer_mvvm.app_kotlin.di.DaggerApplicationComponent
import javax.inject.Inject

internal class BeerViewerApplication : Application(), HasActivityInjector {

    companion object {
        lateinit var instance : BeerViewerApplication
            private set
    }

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        instance = this

        initializeDagger()
        initializeStetho()
        initializeMemoryLeakDetector()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initializeDagger() {
        DaggerApplicationComponent
                .builder()
                .create(this)
                .inject(this)
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeMemoryLeakDetector() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
    }

}
