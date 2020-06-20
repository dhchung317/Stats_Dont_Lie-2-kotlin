package com.hyunki.statsdontlie.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hyunki.statsdontlie.BaseApplication
import com.hyunki.statsdontlie.OnFragmentInteractionListener
import com.hyunki.statsdontlie.R
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.hyunki.statsdontlie.network.ResponseState
import com.hyunki.statsdontlie.view.fragments.GameFragment
import com.hyunki.statsdontlie.view.fragments.MenuFragment
import com.hyunki.statsdontlie.view.fragments.ResultFragment
import com.hyunki.statsdontlie.viewmodel.NewViewModel
import java.util.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private lateinit var playerAverageModels: List<PlayerAverageModel>
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: NewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        viewModel = NewViewModel.getInstance(this)
        viewModelSetUp()
    }

    @SuppressLint("CheckResult")
    private fun viewModelSetUp() {
        val viewModel: NewViewModel = NewViewModel.Companion.getInstance(this)
        viewModel.callBDLResponseClient().observe(this, Observer {
            processResponse(it)
        })
        displayMenuFragment()
    }

    override fun displayMenuFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MenuFragment.Companion.newInstance())
                .commit()
    }

    override fun displayGameFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, GameFragment.Companion.newInstance(), "game")
                .addToBackStack(null)
                .commit()
    }

    override fun displayResultFragment(playerCorrectGuesses: Int, playerIncorrectGuesses: Int) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ResultFragment.Companion.newInstance(playerCorrectGuesses, playerIncorrectGuesses))
                .commit()
    }

    private fun processResponse(res: ResponseState) {
        when (res) {
            is ResponseState.Loading -> {
                showProgressBar(true)
            }
            is ResponseState.Success.OnResponsesLoaded -> {
                playerAverageModels = res.players
                showProgressBar(false)
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