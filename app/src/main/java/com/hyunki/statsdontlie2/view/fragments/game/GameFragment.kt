package com.hyunki.statsdontlie2.view.fragments.game

import android.animation.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
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
import com.hyunki.statsdontlie2.view.fragments.game.delegates.TextSize

import com.hyunki.statsdontlie2.view.viewbinding.viewBinding
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import javax.inject.Inject

//TODO work on animations
//TODO work on ui : placement of name text, edge cases for long names
//TODO refactor logic between gamemanager and fragment
//TODO image size is too small
//TODO results fragment remains in stack
//TODO exit button in game
//TODO change right/wrong blinker to one view
class GameFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment(R.layout.fragment_game), GameCommandsListener {

    private val binding by viewBinding(FragmentGameBinding::bind)

    private val cAnimation by lazy {
        Animations.getChecker(correct)
    }

    private val iAnimation by lazy {
        Animations.getChecker(incorrect)
    }

    private lateinit var listener: OnFragmentInteractionListener

    private lateinit var gameManager: GameManager

    private lateinit var playerOne: PlayerCardView
    private lateinit var playerTwo: PlayerCardView

    private lateinit var countDownView: TextView
    private lateinit var displayQuestionTextView: TextView

    private lateinit var correct: ImageView
    private lateinit var incorrect: ImageView

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
        findViews()
        setViewModel()
        nbaPlayers = viewModel.getPlayerAverageModels()
        gameManager = GameManager(nbaPlayers, this, listener)
        runGame()
    }

    override fun runGame() {
        setViewsWithGameData()
        playerOne.startAnimation(Animations.getFadeIn(playerOne))
        playerTwo.startAnimation(Animations.getFadeIn(playerTwo))
        setPlayer1CardViewOnClick()
        setPlayer2CardViewOnClick()
    }

    override fun runClock() {
        setCountDownTimer()
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
        incorrect = binding.wrong
        correct = binding.right
    }

    private fun setCountDownTimer() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownView.text = (millisUntilFinished / 1000).toString()
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFinish() {
                playerOne.clearAnimation();
                playerTwo.clearAnimation();
                incorrect.clearAnimation();
                correct.clearAnimation();
                listener.displayResultFragment()
                gameManager.setResults()
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(MainViewModel::class.java)
    }

    private fun setViewsWithGameData() {
        val data = gameManager.getRoundData()
        val p1 = data.getPlayer1()
        val p2 = data.getPlayer2()

//autosize does not work for custom fonts

        playerOne.playerNameTextView.textSize = TextSize(p1.firstName).size
        playerTwo.playerNameTextView.textSize = TextSize(p2.firstName).size

        playerOne.playerNameTextView.text = data.getPlayer1().firstName
        playerTwo.playerNameTextView.text = data.getPlayer2().firstName

        setPlayerImage(p1, 1)
        setPlayerImage(p2, 2)

        displayQuestionTextView.text = gameManager.getRoundData().question.question

        toggleStatView(false)
        playerOne.playerStatTextView.setTextColor(resources.getColor(R.color.colorBlack))
        playerTwo.playerStatTextView.setTextColor(resources.getColor(R.color.colorBlack))
        playerOne.playerStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer1Stat())
        playerTwo.playerStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer2Stat())
    }

    private fun toggleStatView(showStat: Boolean) {
        if (showStat) {
            playerOne.playerStatTextView.visibility = View.VISIBLE
            playerTwo.playerStatTextView.visibility = View.VISIBLE
        } else {
            playerOne.playerStatTextView.visibility = View.INVISIBLE
            playerTwo.playerStatTextView.visibility = View.INVISIBLE
        }
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
        val pBottom = requireActivity().window.decorView.bottom
        val set1 = Animations.getCardFlipAnimation(playerOne, pBottom)
        val set2 = Animations.getCardFlipAnimation(playerTwo, pBottom)

        set1.start()
        set1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                set2.start()
            }
        })

        set2.addListener(object : AnimatorListenerAdapter() {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onAnimationEnd(animation: Animator?) {
                toggleStatView(true)
                playerOne.playerImage.alpha = .4f
                playerTwo.playerImage.alpha = .4f

                if (this@GameFragment.isVisible) {
                    Handler().postDelayed({
                        playerOne.startAnimation(Animations.getFadeOut(playerOne));
                        playerTwo.startAnimation(Animations.getFadeOut(playerTwo));
                    }, 900)

                    Handler().postDelayed({
                        playerOne.playerImage.alpha = 1f
                        playerTwo.playerImage.alpha = 1f
                        gameManager.finishRound()
                    }, 1200)
                }
            }
        })
    }

    private fun setPlayer1CardViewOnClick() {
        playerOne.setOnClickListener { v: View? ->
            playerOne.isClickable = false
            playerTwo.isClickable = false
            val check = gameManager.getRoundData().getPlayer1() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playCheckerAnimation(check)
            animateStatView(check, playerOne.playerStatTextView)
            flipViews()
        }
    }

    private fun setPlayer2CardViewOnClick() {
        playerTwo.setOnClickListener { v: View? ->
            playerOne.isClickable = false
            playerTwo.isClickable = false
            val check = gameManager.getRoundData().getPlayer2() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playCheckerAnimation(check)
            animateStatView(check, playerTwo.playerStatTextView)
            flipViews()
        }
    }

    private fun animateStatView(isCorrect: Boolean, textView: TextView) {
        if (isCorrect) {
            textView.setTextColor(
                    resources.getColor(R.color.colorGreen))
        } else {
            textView.setTextColor(
                    resources.getColor(R.color.colorErrorRed))
        }
    }

    private fun playCheckerAnimation(check: Boolean) {
        if (check) {
            correct.bringToFront()
            correct.startAnimation(cAnimation)
        } else {
            incorrect.bringToFront()
            incorrect.startAnimation(iAnimation)
        }
    }

    private fun roundResults(isRightPlayer: Boolean) {
        gameManager.checkAnswer(isRightPlayer)
    }

    companion object {
        private const val TAG = "GameFragment"
    }
}