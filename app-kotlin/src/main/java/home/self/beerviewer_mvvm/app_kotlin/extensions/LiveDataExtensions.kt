package home.self.beerviewer_mvvm.app_kotlin.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import home.self.beerviewer_mvvm.app_kotlin.util.SimpleAction

internal fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, block: (T) -> Unit, onNullAction: SimpleAction? = null) = liveData.observe(this, Observer{
        it?.run {
            block(it)
        } ?: onNullAction?.invoke()
})
