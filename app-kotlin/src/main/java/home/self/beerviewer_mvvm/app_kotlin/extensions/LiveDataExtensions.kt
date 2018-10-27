package home.self.beerviewer_mvvm.app_kotlin.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import home.self.beerviewer_mvvm.app_kotlin.util.SimpleAction

internal fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, block: (T) -> Unit, onNullAction: SimpleAction? = null) = liveData.observe(this, Observer{
        it?.run {
            block(it)
        } ?: onNullAction?.invoke()
})
