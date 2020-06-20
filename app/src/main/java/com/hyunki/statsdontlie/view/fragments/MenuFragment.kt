package com.hyunki.statsdontlie.view.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hyunki.statsdontlie.OnFragmentInteractionListener
import com.hyunki.statsdontlie.R
import com.hyunki.statsdontlie.viewmodel.NewViewModel

class MenuFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var playButton: Button? = null
    private var progressDialog: ProgressDialog? = null
    private var viewModel: NewViewModel? = null
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
        viewModel = ViewModelProviders.of(this).get(NewViewModel::class.java)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playButton = view.findViewById(R.id.play_button)
        playButton.setOnClickListener(View.OnClickListener { v: View? -> listener!!.displayGameFragment() })
    }

    fun showProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.setTitle("Download")
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }
}