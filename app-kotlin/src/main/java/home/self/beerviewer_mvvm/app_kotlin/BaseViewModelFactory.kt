package home.self.beerviewer_mvvm.app_kotlin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

internal class BaseViewModelFactory @Inject constructor(
        private val creators: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
                ?: creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
                ?: throw IllegalArgumentException("unknown model class " + modelClass)

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}