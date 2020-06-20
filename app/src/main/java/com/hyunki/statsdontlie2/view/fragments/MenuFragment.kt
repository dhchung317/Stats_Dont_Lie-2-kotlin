package com.hyunki.statsdontlie2.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.view.NewViewModel
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MenuFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit  var playButton: Button
    private lateinit var viewModel: NewViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
        if (fragmentManager!!.findFragmentByTag("game") != null) {
            fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag("game")!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity(),viewModelProviderFactory).get(NewViewModel::class.java)
        playButton = view.findViewById(R.id.play_button)
        playButton.setOnClickListener(View.OnClickListener { v: View? -> listener!!.displayGameFragment() })
    }

//    fun showProgressDialog() {
//        progressDialog = ProgressDialog(context)
//        progressDialog!!.setMessage("Loading...")
//        progressDialog!!.setTitle("Download")
//        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//        progressDialog!!.setCanceledOnTouchOutside(false)
//        progressDialog!!.show()
//    }
}