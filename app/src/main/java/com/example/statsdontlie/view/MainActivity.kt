package com.example.statsdontlie.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.statsdontlie.OnFragmentInteractionListener
import com.example.statsdontlie.R
import com.example.statsdontlie.model.PlayerAverageModel
import com.example.statsdontlie.view.fragments.GameFragment
import com.example.statsdontlie.view.fragments.MenuFragment
import com.example.statsdontlie.view.fragments.ResultFragment
import com.example.statsdontlie.viewmodel.NewViewModel
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
                .subscribe({ playerAverageModel: PlayerAverageModel? ->
                    //add directly to the database
                    viewModel.databaseRepository.addPlayerData(playerAverageModel)
                    playerAverageModels.add(playerAverageModel)
                    Log.d("TAG", "List<PlayerAverageModel> size: " + playerAverageModels.size)
                }, { throwable: Throwable -> Log.d("TAG", throwable.toString()) }
                ) { Log.d("TAG", "OnComplete - List<PlayerAverageModel> size: " + playerAverageModels.size) }
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