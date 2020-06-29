package com.hyunki.statsdontlie2.view.fragments.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.view.MainViewModel
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject
//TODO exit button
class MenuFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit  var playButton: Button
    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
        if (parentFragmentManager.findFragmentByTag("game") != null) {
            parentFragmentManager.beginTransaction().remove(parentFragmentManager.findFragmentByTag("game")!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity(),viewModelProviderFactory).get(MainViewModel::class.java)
        playButton = view.findViewById(R.id.play_button)
        playButton.setOnClickListener(View.OnClickListener { v: View? -> listener!!.displayGameFragment() })
    }
}