package home.self.beerviewer_mvvm.app_kotlin.extensions

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity


fun FragmentActivity.showDialogFragment(fragment: Fragment, tag: String = "", commitAllowStateLoss: Boolean = true) {
    val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .add(fragment, tag)

    if(commitAllowStateLoss) {
        fragmentTransaction.commitAllowingStateLoss()
    } else {
        fragmentTransaction.commit()
    }
}

fun FragmentActivity.hideDialogFragment(vararg tags: String) {
    val fragmentList = mutableListOf<DialogFragment>()

    tags.forEach {
        val fragment = supportFragmentManager.findFragmentByTag(it)

        if(fragment != null && fragment is DialogFragment) {
            fragmentList.add(fragment)
        }
    }

    fragmentList.forEach {
        it.dismissAllowingStateLoss()
    }
}
