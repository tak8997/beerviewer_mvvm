package home.self.beerviewer_mvvm.app_kotlin.data.model

import java.io.Serializable


data class Delay(val value: Long): Serializable {
    companion object {
        @JvmStatic val ZERO = Delay(0L)
    }
}