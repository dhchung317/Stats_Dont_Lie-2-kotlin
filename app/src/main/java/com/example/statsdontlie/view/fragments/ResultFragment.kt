package com.example.statsdontlie.view.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.statsdontlie.OnFragmentInteractionListener
import com.example.statsdontlie.R
import com.example.statsdontlie.constants.BDLAppConstants

class ResultFragment : Fragment() {
    private var correct = 0
    private var wrong = 0
    private var result: TextView? = null
    private var menu: Button? = null
    private var restart: Button? = null
    private var listener: OnFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            correct = arguments!!.getInt(BDLAppConstants.CORRECT, -1)
            wrong = arguments!!.getInt(BDLAppConstants.WRONG, -1)
        }
        val mp = MediaPlayer.create(context, R.raw.balldontlie)
        mp.start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    fun initializeViews(view: View) {
        result = view.findViewById(R.id.answer_results)
        menu = view.findViewById(R.id.menu_btn)
        restart = view.findViewById(R.id.restart_btn)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        val concat_results = "Correct Answers: \n$correct\n\nWrong Answers: \n$wrong"
        result!!.text = concat_results
        clickEvents()
    }

    fun clickEvents() {
        menu!!.setOnClickListener { v: View? -> listener!!.displayMenuFragment() }
        restart!!.setOnClickListener { v: View? -> listener!!.displayGameFragment() }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance(correct: Int, incorrect: Int): ResultFragment {
            val resultFragment = ResultFragment()
            val bundle = Bundle()
            bundle.putInt(BDLAppConstants.CORRECT, correct)
            bundle.putInt(BDLAppConstants.WRONG, incorrect)
            resultFragment.arguments = bundle
            return resultFragment
        }
    }
}