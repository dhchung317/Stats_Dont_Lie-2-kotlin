package com.hyunki.statsdontlie2.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.BaseApplication
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.model.NBAPlayer
import com.hyunki.statsdontlie2.network.ResponseState
import com.hyunki.statsdontlie2.view.fragments.GameFragment
import com.hyunki.statsdontlie2.view.fragments.MenuFragment
import com.hyunki.statsdontlie2.view.fragments.ResultFragment
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    @Inject
    lateinit var menuFragment: MenuFragment

    @Inject
    lateinit var gameFragment: GameFragment

    @Inject
    lateinit var resultFragment: ResultFragment

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var NBAPlayers: List<NBAPlayer>
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: NewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this,providerFactory).get(NewViewModel::class.java)
        initViewModel()
    }

    @SuppressLint("CheckResult")
    private fun initViewModel() {
        viewModel.callBDLResponseClient().observe(this, Observer {
            processResponse(it)
        })
    }

    override fun displayMenuFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, menuFragment)
                .commit()
    }

    override fun displayGameFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, gameFragment, "game")
                .addToBackStack(null)
                .commit()
    }

    override fun displayResultFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, resultFragment)
                .commit()
    }

    override fun setResultsDataFromGameFragment(playerCorrectGuesses: Int, playerIncorrectGuesses: Int) {
        viewModel.setCorrectGuesses(playerCorrectGuesses)
        viewModel.setIncorrectGuesses(playerIncorrectGuesses)
    }

    private fun processResponse(res: ResponseState) {
        when (res) {
            is ResponseState.Loading -> {
                showProgressBar(true)
            }
            is ResponseState.Success.OnResponsesLoaded -> {
                NBAPlayers = res.NBAPlayers
                Log.d(TAG, "processResponse: " + res.NBAPlayers.size)

                viewModel.saveAllPlayers(res.NBAPlayers)
                showProgressBar(false)
                displayMenuFragment()
            }
            is ResponseState.Error -> {
                showProgressBar(false)
                Log.d(TAG, "processResponse: " + res.e)
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val TAG = "main-activity"
    }

}