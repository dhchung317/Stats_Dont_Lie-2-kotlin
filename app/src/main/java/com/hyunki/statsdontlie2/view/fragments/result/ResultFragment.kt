package com.hyunki.statsdontlie2.view.fragments.result

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
import com.hyunki.statsdontlie2.databinding.FragmentResultBinding
import com.hyunki.statsdontlie2.view.MainViewModel
import com.hyunki.statsdontlie2.view.viewbinding.viewBinding
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject
//TODO exit button
class ResultFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment(R.layout.fragment_result) {
    val binding by viewBinding(FragmentResultBinding::bind)

    private lateinit var viewModel: MainViewModel
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
        result = binding.answerResults
        menu = binding.menuBtn
        restart = binding.restartBtn
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(MainViewModel::class.java)
        val concatResults
                = "Correct Answers: \n${viewModel.getCorrectGuesses()}\n\nWrong Answers: \n${viewModel.getIncorrectGuesses()}"
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