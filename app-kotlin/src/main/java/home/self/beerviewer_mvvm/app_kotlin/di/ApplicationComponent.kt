package home.self.beerviewer_mvvm.app_kotlin.di


import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import home.self.beerviewer_mvvm.app_kotlin.BeerViewerApplication
import javax.inject.Singleton

/**
 * This is a Dagger component. Refer to [BeerViewerApplication] for the list of Dagger components
 * used in this application.
 *
 *
 * Even though Dagger allows annotating a [Component] as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in [ ].
 * //[AndroidSupportInjectionModule]
 * // is the module from Dagger.Android that helps with the generation
 * // and location of subcomponents.
 */

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApplicationModule::class,
        ApplicationRepositoryModule::class
)
)
internal interface ApplicationComponent : AndroidInjector<BeerViewerApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<BeerViewerApplication>()
}
