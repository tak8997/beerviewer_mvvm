package home.self.beerviewer_mvvm.app_kotlin


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

internal class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_loading_dialog, container, false)

        return view
    }


}
