package com.hyunki.statsdontlie2.view.fragments.game

import android.animation.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.Animations
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.databinding.FragmentGameBinding
import com.hyunki.statsdontlie2.model.NBAPlayer
import com.hyunki.statsdontlie2.view.fragments.game.utils.GameManager
import com.hyunki.statsdontlie2.utils.PlayerUtil
import com.hyunki.statsdontlie2.view.MainViewModel
import com.hyunki.statsdontlie2.view.fragments.game.controller.GameCommandsListener
import com.hyunki.statsdontlie2.view.fragments.game.customviews.PlayerCardView
import com.hyunki.statsdontlie2.view.fragments.game.utils.GameRoundData

import com.hyunki.statsdontlie2.view.viewbinding.viewBinding
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.reflect.KProperty

//TODO work on animations
//TODO refactor logic between gamemanager and fragment
//TODO results fragment remains in stack
//TODO exit button in game
class GameFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment(R.layout.fragment_game), GameCommandsListener {
    private val binding by viewBinding(FragmentGameBinding::bind)

    private lateinit var playerOne: PlayerCardView
    private lateinit var playerTwo: PlayerCardView
    private lateinit var countDownView: TextView
    private lateinit var displayQuestionTextView: TextView
    private lateinit var blinker: ImageView

    private lateinit var listener: OnFragmentInteractionListener
    private lateinit var gameManager: GameManager
    private lateinit var viewModel: MainViewModel

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var nbaPlayers: List<NBAPlayer>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        findViews()
        setViewModel()
        nbaPlayers = viewModel.getPlayerAverageModels()
        gameManager = GameManager(nbaPlayers, this, listener)
        runGame()
    }

    override fun runGame() {
        toggleCardClickable(true)
        setViewsWithGameData(gameManager.getRoundData())
        playerOne.startAnimation(Animations.getFadeIn(playerOne))
        playerTwo.startAnimation(Animations.getFadeIn(playerTwo))
        setPlayer1CardViewOnClick()
        setPlayer2CardViewOnClick()
    }

    override fun runClock() {
        setCountDownTimer(5000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
    }

    private fun findViews() {
        playerOne = binding.playerOne
        playerTwo = binding.playerTwo
        displayQuestionTextView = binding.questionDisplayTextView
        countDownView = binding.countDownTimer
        blinker = binding.blinker
    }

    private fun setCountDownTimer(long: Long) {
        countDownTimer = object : CountDownTimer(long, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownView.text = (millisUntilFinished / 1000).toString()
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFinish() {
//                playerOne.clearAnimation();
//                playerTwo.clearAnimation();
//                blinker.clearAnimation();
                listener.displayResultFragment()
                gameManager.setResults()
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(MainViewModel::class.java)
    }

    private fun setViewsWithGameData(data: GameRoundData) {
        val p1 = data.getPlayer1()
        val p2 = data.getPlayer2()
        //autosize does not work for custom fonts
        playerOne.playerNameLength = p1.firstName.length
        playerTwo.playerNameLength = p2.firstName.length

        playerOne.playerName = data.getPlayer1().firstName
        playerTwo.playerName = data.getPlayer2().firstName

        setPlayerImage(p1, 1)
        setPlayerImage(p2, 2)

        displayQuestionTextView.text = gameManager.getRoundData().question.qString

        toggleStatView(false)

        playerOne.playerStat = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer1Stat())
        playerTwo.playerStat = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer2Stat())
    }

    private fun toggleStatView(showStat: Boolean) {
        if (showStat) {
            playerOne.playerStatVisibility = true
            playerTwo.playerStatVisibility = true
        } else {
            playerOne.playerStatVisibility = false
            playerTwo.playerStatVisibility = false
        }
    }

    private fun setAlpha(f: Float) {
        playerOne.playerImage.alpha = f
        playerTwo.playerImage.alpha = f
    }

    private fun setPlayerImage(player: NBAPlayer, number: Int) {
        lateinit var v: PlayerCardView
        when (number) {
            1 -> v = playerOne
            2 -> v = playerTwo
        }
        val bitmap = viewModel.getImageFromDatabase(player.playerID.toInt())
        if (bitmap != null) {
            v.playerImage.setImageBitmap(bitmap)
        } else {
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(player.firstName, player.lastName))
                    .into(v.playerImage)
        }
    }

    private fun flipViews() {

        val set1 = Animations.getCardFlipAnimation(playerOne, requireActivity().window.decorView.bottom)
        val set2 = Animations.getCardFlipAnimation(playerTwo, requireActivity().window.decorView.bottom)


        Log.d(TAG, "flipViews: " + set1?.childAnimations.toString())
        set1?.start()
        Log.d("animations", "flipViews: called")
        set1?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                set2?.start()
            }
        })

        set2?.addListener(object : AnimatorListenerAdapter() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onAnimationEnd(animation: Animator?) {
                toggleStatView(true)
                setAlpha(.4f)
                if (this@GameFragment.isVisible) {
                    Handler().postDelayed({
                        playerOne.startAnimation(Animations.getFadeOut(playerOne));
                        playerTwo.startAnimation(Animations.getFadeOut(playerTwo));
                    }, 900)
                    Handler().postDelayed({
                        setAlpha(1f)
                        gameManager.finishRound()
                    }, 1200)
                    gameManager.addRoundData()
                }
            }
        })
    }

    private fun setPlayer1CardViewOnClick() {
        playerOne.setOnClickListener { v: View? ->
            toggleCardClickable(false)
            val check = gameManager.getRoundData().getPlayer1() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playBlinkerAnimation(check)
            colorizeStatView(check, playerOne.playerStatTextView, playerTwo.playerStatTextView)
            flipViews()
        }
    }

    private fun setPlayer2CardViewOnClick() {
        playerTwo.setOnClickListener { v: View? ->
            toggleCardClickable(false)
            val check = gameManager.getRoundData().getPlayer2() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playBlinkerAnimation(check)
            colorizeStatView(check, playerTwo.playerStatTextView, playerOne.playerStatTextView)
            flipViews()
        }
    }

    private fun toggleCardClickable(boolean: Boolean) {
        Log.d("animations", "toggleCardClickable: $boolean")
        playerOne.isClickable = boolean
        playerTwo.isClickable = boolean
        playerOne.isEnabled = boolean
        playerTwo.isEnabled = boolean
    }

    private fun colorizeStatView(isCorrect: Boolean, v: TextView, v2: TextView) {
        if (isCorrect) {
            v.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorGreen))
        } else {
            v.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorErrorRed))
        }
        v2.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBlack))
    }

    private fun playBlinkerAnimation(check: Boolean) {
        when (check) {
            true -> blinker.setImageResource(R.drawable.correct)
            else -> blinker.setImageResource(R.drawable.incorrect)
        }
        blinker.bringToFront()
        blinker.startAnimation(Animations.getChecker(blinker))
    }

    private fun roundResults(isRightPlayer: Boolean) {
        gameManager.checkAnswer(isRightPlayer)
    }

    companion object {
        private const val TAG = "GameFragment"
    }
}
