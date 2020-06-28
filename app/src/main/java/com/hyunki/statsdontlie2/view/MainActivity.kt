package com.hyunki.statsdontlie2.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.BaseApplication
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.databinding.ActivityMainBinding
import com.hyunki.statsdontlie2.network.ResponseState
import com.hyunki.statsdontlie2.view.fragments.game.GameFragment
import com.hyunki.statsdontlie2.view.fragments.menu.MenuFragment
import com.hyunki.statsdontlie2.view.fragments.result.ResultFragment
import com.hyunki.statsdontlie2.view.viewbinding.viewBinding
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    @Inject
    lateinit var menuFragment: MenuFragment
    @Inject
    lateinit var gameFragment: GameFragment
    @Inject
    lateinit var resultFragment: ResultFragment
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = binding.progressBar
        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)

        val networkSharedPreference = getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.saved_network_call_preference_key), false)

        if (!networkSharedPreference) {
            initViewModel()
        } else {
            displayMenuFragment()
        }
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

    override fun setResultsDataFromGameManager(playerCorrectGuesses: Int, playerIncorrectGuesses: Int) {
        viewModel.setCorrectGuesses(playerCorrectGuesses)
        viewModel.setIncorrectGuesses(playerIncorrectGuesses)
    }

    private fun processResponse(res: ResponseState) {
        when (res) {
            is ResponseState.Loading -> {
                showProgressBar(true)
            }
            is ResponseState.Success.OnResponsesLoaded -> {
                viewModel.saveAllPlayers(res.NBAPlayers)
                addSharedPreferenceForNetworkCall()
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

    private fun addSharedPreferenceForNetworkCall() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.saved_network_call_preference_key), true)
            commit()
        }
    }

    companion object {
        const val TAG = "main-activity"
    }
}