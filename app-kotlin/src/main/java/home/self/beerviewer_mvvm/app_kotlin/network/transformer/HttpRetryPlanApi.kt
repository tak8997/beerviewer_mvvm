package home.self.beerviewer_mvvm.app_kotlin.network.transformer

import home.self.beerviewer_mvvm.app_kotlin.data.model.HttpUnsucessfulCause


internal interface HttpRetryPlanApi {
    fun apply(httpUnsucessfulCause: HttpUnsucessfulCause): Boolean
}