package com.hyunki.statsdontlie2.view.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.view.NewViewModel
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class ResultFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment() {

    private lateinit var viewModel: NewViewModel
    private lateinit var result: TextView
    private lateinit var menu: Button
    private lateinit var restart: Button
    private lateinit var mp: MediaPlayer
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mp = MediaPlayer.create(context, R.raw.balldontlie)
        mp.start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    private fun initializeViews(view: View) {
        result = view.findViewById(R.id.answer_results)
        menu = view.findViewById(R.id.menu_btn)
        restart = view.findViewById(R.id.restart_btn)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(NewViewModel::class.java)
        val concatResults = "Correct Answers: \n${viewModel.getCorrectGuesses()}\n\nWrong Answers: \n${viewModel.getIncorrectGuesses()}"
        result.text = concatResults
        clickEvents()
    }

    private fun clickEvents() {
        menu.setOnClickListener { v: View? -> listener.displayMenuFragment() }
        restart.setOnClickListener { v: View? -> listener.displayGameFragment() }
    }

    override fun onDetach() {
        super.onDetach()
        mp.release()
//        listener = null
    }
}