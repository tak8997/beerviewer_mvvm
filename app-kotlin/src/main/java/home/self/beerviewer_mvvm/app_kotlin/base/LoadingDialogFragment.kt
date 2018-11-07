package home.self.beerviewer_mvvm.app_kotlin.base


import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import home.self.beerviewer_mvvm.app_kotlin.R


internal class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_loading_dialog, container, false)

}
