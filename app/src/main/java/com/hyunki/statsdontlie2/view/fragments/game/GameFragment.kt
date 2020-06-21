package com.hyunki.statsdontlie2.view.fragments.game

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private lateinit var viewModel: NewViewModel

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var nbaPlayers: List<NBAPlayer>

    private lateinit var correct: ImageView
    private lateinit var incorrect: ImageView

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
        //        handler = new Handler();
//        handler2 = new Handler();
    }

    private fun setCountDownTimer() {
        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownView.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
//                playerOneCardView.clearAnimation();
//                playerTwoCardView.clearAnimation();
//                incorrect.clearAnimation();
//                correct.clearAnimation();
//                handler.removeCallbacksAndMessages(null);
//                handler2.removeCallbacksAndMessages(null);

                listener.displayResultFragment()
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(NewViewModel::class.java)
    }

    private fun setViews() {
        val data = gameManager.getRoundData()
        val p1 = data.getPlayer1()
        val p2 = data.getPlayer2()

        playerOneTextView.text = data.getPlayer1().firstName
        playerTwoTextView.text = data.getPlayer2().firstName

        setPlayerOneImage(p1)
        setPlayerTwoImage(p2)

        displayQuestionTextView.text = gameManager.getRoundData().question.question

        playerOneStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer1Stat())
        playerTwoStatTextView.text = DecimalFormat("#.#").format(gameManager.getRoundData().getPlayer2Stat())
    }

    private fun setPlayerOneImage(p1: NBAPlayer) {
        val bitmap = viewModel.getImageFromDatabase(p1.playerID.toInt())
        if (bitmap != null) {
            playerOneImage.setImageBitmap(bitmap)
        } else {
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(p1.firstName, p1.lastName))
                    .into(playerOneImage)
        }
    }

    private fun setPlayerTwoImage(p2: NBAPlayer) {
        val bitmap = viewModel.getImageFromDatabase(p2.playerID.toInt())
        if (bitmap != null) {
            playerTwoImage.setImageBitmap(bitmap)
        } else {
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(p2.firstName, p2.lastName))
                    .into(playerOneImage)
        }
    }

    private fun flipViews() {
        val flip = AnimationUtils.loadAnimation(activity, R.anim.card)
        val flipTwo = AnimationUtils.loadAnimation(activity, R.anim.card)

        playerOneCardView.startAnimation(flip);
        playerOneCardView.setClickable(false);
        playerTwoCardView.setClickable(false);
        val timer: CountDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
//                reloadPlayersAndViews()
//                runGame()
                gameManager.finishRound()

            }
        }
        timer.start()

        flip.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                playerTwoCardView.startAnimation(flipTwo);
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })


        flipTwo.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

                    Handler().postDelayed({
                        if ((parentFragmentManager.findFragmentByTag("game") != null) &&
                                parentFragmentManager.findFragmentByTag("game")!!.isVisible) {
                            playerOneCardView.startAnimation(Animations.getFadeOut(playerOneCardView));
                            playerTwoCardView.startAnimation(Animations.getFadeOut(playerTwoCardView));
                        }
                        },900)
            }


            override fun onAnimationStart(animation: Animation?) {

            }

        })

    }

    private fun setPlayer1CardView() {
        playerOneCardView.setOnClickListener { v: View? ->
            val check = gameManager.getRoundData().getPlayer1() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playBooleanAnimation(check)
            flipViews()
        }
    }

    private fun setPlayer2CardView() {
        playerTwoCardView.setOnClickListener { v: View? ->
            val check = gameManager.getRoundData().getPlayer2() == gameManager.getRoundData().getAnswer()
            roundResults(check)
            playBooleanAnimation(check)
            flipViews()
        }
    }

    private fun playBooleanAnimation(check: Boolean) {
        if (check) {
            correct.startAnimation(Animations.getChecker(correct))
        } else {
            incorrect.startAnimation(Animations.getChecker(incorrect))
        }
    }

    private fun roundResults(isRightPlayer: Boolean) {
        gameManager.checkAnswer(isRightPlayer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
    }

    companion object {
        private const val TAG = "GameFragment"
    }

    override fun runGame() {
        setViews()
        playerOneCardView.startAnimation(Animations.getFadeIn(playerOneCardView))
        playerTwoCardView.startAnimation(Animations.getFadeIn(playerTwoCardView))
        setPlayer1CardView()
        setPlayer2CardView()
    }

    override fun runClock() {
        setCountDownTimer()
    }
}