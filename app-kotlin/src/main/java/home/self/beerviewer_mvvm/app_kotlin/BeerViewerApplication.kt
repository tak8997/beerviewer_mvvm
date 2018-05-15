package home.self.beerviewer_mvvm.app_kotlin

import android.app.Activity
import android.app.Application

import com.squareup.leakcanary.LeakCanary

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import home.self.beerviewer_mvvm.app_kotlin.di.DaggerAppComponent

class BeerViewerApplication : DaggerApplication() {

    @Inject lateinit var activityDispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this

        initMemoryLeakDetection()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    private fun initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return

        LeakCanary.install(this)
    }

    companion object {

        lateinit var instance : BeerViewerApplication
            private set
    }

}
