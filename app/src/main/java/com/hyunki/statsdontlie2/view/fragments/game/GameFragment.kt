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
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hyunki.statsdontlie2.Animations
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.model.NBAPlayer
import com.hyunki.statsdontlie2.view.fragments.game.utils.GameManager
import com.hyunki.statsdontlie2.utils.PlayerUtil
import com.hyunki.statsdontlie2.view.NewViewModel
import com.hyunki.statsdontlie2.view.fragments.game.controller.GameCommandsListener
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import javax.inject.Inject

//TODO work on animations
//TODO work on ui : placement of name text, edge cases for long names
//TODO refactor logic between gamemanager and fragment
class GameFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment(), GameCommandsListener {
    private lateinit var listener: OnFragmentInteractionListener

    private lateinit var gameManager: GameManager

    private lateinit var playerOneCardView: CardView
    private lateinit var playerTwoCardView: CardView

    private lateinit var playerOneImage: ImageView
    private lateinit var playerTwoImage: ImageView

    private lateinit var playerOneTextView: TextView
    private lateinit var playerTwoTextView: TextView

    private lateinit var playerOneStatTextView: TextView
    private lateinit var playerTwoStatTextView: TextView

    private lateinit var countDownView: TextView
    private lateinit var displayQuestionTextView: TextView

    private lateinit var correct: ImageView
    private lateinit var incorrect: ImageView

    private lateinit var viewModel: NewViewModel

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
        findViews(view)
        setViewModel()
        nbaPlayers = viewModel.getPlayerAverageModels()
        gameManager = GameManager(nbaPlayers, this, listener)
        runGame()
    }

    override fun runGame() {
        setViewsWithGameData()
        playerOneCardView.startAnimation(Animations.getFadeIn(playerOneCardView))
        playerTwoCardView.startAnimation(Animations.getFadeIn(playerTwoCardView))
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

    private fun findViews(view: View) {
        playerOneCardView = view.findViewById(R.id.player_one)
        playerTwoCardView = view.findViewById(R.id.player_two)
        playerOneImage = view.findViewById(R.id.playerOne_imageView)
        playerTwoImage = view.findViewById(R.id.playerTwo_imageView)
        playerOneTextView = view.findViewById(R.id.player_one_text_view)
        playerTwoTextView = view.findViewById(R.id.player_two_text_view)
        playerOneStatTextView = view.findViewById(R.id.player_one_stat_text_view)
        playerTwoStatTextView = view.findViewById(R.id.player_two_stat_text_view)
        displayQuestionTextView = view.findViewById(R.id.question_display_text_view)
        countDownView = view.findViewById(R.id.count_down_timer)
        incorrect = view.findViewById(R.id.wrong)
        correct = view.findViewById(R.id.right)
    }

    private fun setCountDownTimer() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownView.text = (millisUntilFinished / 1000).toString()
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFinish() {
                playerOneCardView.clearAnimation();
                playerTwoCardView.clearAnimation();
                incorrect.clearAnimation();
                correct.clearAnimation();
                listener.displayResultFragment()
                gameManager.setResults()
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(NewViewModel::class.java)
    }

    private fun setViewsWithGameData() {
        val data = gameManager.getRoundData()
        val p1 = data.getPlayer1()
        val p2 = data.getPlayer2()

        if (isNameLengthTooLong(p1.firstName)) {
            playerOneTextView.textSize = 34f
        } else {
            playerOneTextView.textSize = 36f
        }

        if (isNameLengthTooLong(p2.firstName)) {
            playerTwoTextView.textSize = 34f
        } else {
            playerTwoTextView.textSize = 36f
        }

        playerOneTextView.text = data.getPlayer1().firstName
        playerTwoTextView.text = data.getPlayer2().firstName

        setPlayerImage(p1, 1)
        setPlayerImage(p2, 2)

        displayQuestionTextView.text = gameManager.getRoundData().question.question

        toggleStatView(false)
        playerOneStatTextView.setTextColor(resources.getColor(R.color.colorBlack))
        playerTwoStatTextView.setTextColor(resources.getColor(R.color.colorBlack))
        playerOneStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer1Stat())
        playerTwoStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer2Stat())
    }

    private fun toggleStatView(showStat: Boolean) {
        if (showStat) {
            playerOneStatTextView.visibility = View.VISIBLE
            playerTwoStatTextView.visibility = View.VISIBLE
        } else {
            playerOneStatTextView.visibility = View.INVISIBLE
            playerTwoStatTextView.visibility = View.INVISIBLE
        }
    }

    private fun setPlayerImage(player: NBAPlayer, number: Int) {
        lateinit var imageView: ImageView
        when(number) {
            1 -> imageView = playerOneImage
            2 -> imageView = playerTwoImage
        }
        val bitmap = viewModel.getImageFromDatabase(player.playerID.toInt())
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(player.firstName, player.lastName))
                    .into(imageView)
        }
    }

    private fun flipViews() {
        val pBottom = requireActivity().window.decorView.bottom
        val set1 = Animations.getCardFlipAnimation(playerOneCardView, pBottom)
        val set2 = Animations.getCardFlipAnimation(playerTwoCardView, pBottom)

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
                playerOneImage.alpha = .4f
                playerTwoImage.alpha = .4f

                if (this@GameFragment.isVisible) {
                    Handler().postDelayed({
                        playerOneCardView.startAnimation(Animations.getFadeOut(playerOneCardView));
                        playerTwoCardView.startAnimation(Animations.getFadeOut(playerTwoCardView));
                    }, 900)

                    Handler().postDelayed({
                        playerOneImage.alpha = 1f
                        playerTwoImage.alpha = 1f
                        gameManager.finishRound()
                    }, 1200)
                }
            }
        })
    }

    private fun setPlayer1CardViewOnClick() {
        playerOneCardView.setOnClickListener { v: View? ->
            playerOneCardView.isClickable = false
            playerTwoCardView.isClickable = false
            val check = gameManager.getRoundData().getPlayer1() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playCheckerAnimation(check)
            animateStatView(check, playerOneStatTextView)
            flipViews()
        }
    }

    private fun setPlayer2CardViewOnClick() {
        playerTwoCardView.setOnClickListener { v: View? ->
            playerOneCardView.isClickable = false
            playerTwoCardView.isClickable = false
            val check = gameManager.getRoundData().getPlayer2() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playCheckerAnimation(check)
            animateStatView(check, playerTwoStatTextView)
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
            correct.startAnimation(Animations.getChecker(correct))
        } else {
            incorrect.startAnimation(Animations.getChecker(incorrect))
        }
    }

    private fun roundResults(isRightPlayer: Boolean) {
        gameManager.checkAnswer(isRightPlayer)
    }

    private fun isNameLengthTooLong(name: String): Boolean {
        return name.length > 8
    }

    companion object {
        private const val TAG = "GameFragment"
    }
}