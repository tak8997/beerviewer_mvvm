package home.self.beerviewer_mvvm.app_kotlin.base

enum class Parameter {
    CLICK, NULL, SUCCESS, EVENT
}

object Constants {

    val BASE_URL = "http://api.punkapi.com/v2/"

    val DB_NAME = "beer_db"
    val KEY_BEAR_ID = "bearId"
    val REFRESH_DELAY: Long = 2000

    val EXTRA_DEFAULT_PAGE = "extra_default_page"
}
