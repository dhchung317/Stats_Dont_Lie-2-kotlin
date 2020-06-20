package com.hyunki.statsdontlie.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hyunki.statsdontlie.OnFragmentInteractionListener
import com.hyunki.statsdontlie.R
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.hyunki.statsdontlie.view.fragments.GameFragment
import com.hyunki.statsdontlie.view.fragments.MenuFragment
import com.hyunki.statsdontlie.view.fragments.ResultFragment
import com.hyunki.statsdontlie.viewmodel.NewViewModel
import java.util.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private val playerAverageModels: MutableList<PlayerAverageModel?> = ArrayList()
    var viewModel: NewViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = NewViewModel.Companion.getInstance(this)
        viewModelSetUp()
    }

    @SuppressLint("CheckResult")
    private fun viewModelSetUp() {
        val viewModel: NewViewModel = NewViewModel.Companion.getInstance(this)
        viewModel.callBDLResponseClient()
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
}