package com.hyunki.statsdontlie.view.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hyunki.statsdontlie.Animations
import com.hyunki.statsdontlie.OnFragmentInteractionListener
import com.hyunki.statsdontlie.R
import com.hyunki.statsdontlie.constants.BDLAppConstants
import com.hyunki.statsdontlie.model.PlayerAverageModel
import com.hyunki.statsdontlie.utils.GameJudger
import com.hyunki.statsdontlie.utils.PlayerUtil
import com.hyunki.statsdontlie.utils.RandomNumberGenerator
import com.hyunki.statsdontlie.viewmodel.NewViewModel
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class GameFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private var playerOneCardView: CardView? = null
    private var playerTwoCardView: CardView? = null

    private var playerOneImage: ImageView? = null
    private var playerTwoImage: ImageView? = null

    private var playerOneTextView: TextView? = null
    private var playerTwoTextView: TextView? = null

    private var playerOneStatTextView: TextView? = null
    private var playerTwoStatTextView: TextView? = null

    private var countDownView: TextView? = null
    private var displayQuestionTextView: TextView? = null

    private var player1: PlayerAverageModel? = null
    private var player2: PlayerAverageModel? = null
    private var viewModel: NewViewModel? = null
    private var playerCorrectGuesses = 0
    private var playerInCorrectGuesses = 0
    private var countDownTimer: CountDownTimer? = null
    private var playerAverageModels: List<PlayerAverageModel?>? = null
    private var randomQuestionPosition = 0
    private var correct: ImageView? = null
    private var incorrect: ImageView? = null
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
        playerAverageModels = viewModel!!.playerAverageModels
        setCountDownTimer()
        setRandomPlayers(playerAverageModels)
        setViews()

//        observeViewModel();
//        playerOneCardView.startAnimation(Animations.getFadeIn(playerOneCardView));
//        playerTwoCardView.startAnimation(Animations.getFadeIn(playerTwoCardView));
        setPlayer1CardView()
        setPlayer2CardView()
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
                countDownView!!.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
//                playerOneCardView.clearAnimation();
//                playerTwoCardView.clearAnimation();
//                incorrect.clearAnimation();
//                correct.clearAnimation();
//                handler.removeCallbacksAndMessages(null);
//                handler2.removeCallbacksAndMessages(null);
                listener!!.displayResultFragment(playerCorrectGuesses, playerInCorrectGuesses)
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(NewViewModel::class.java)
        //        viewModel.callBDLResponseClient();
    }

    //
    private fun observeViewModel() {
        playerAverageModels = viewModel!!.playerAverageModels
    }

    private fun setRandomPlayers(playerAverageModels: List<PlayerAverageModel?>?) {
        player1 = playerAverageModels!![RandomNumberGenerator.getRandomNumber1()]
        player2 = playerAverageModels[RandomNumberGenerator.getRandomNumber2()]
    }

    private fun setViews() {
        playerOneTextView.setText(player1.getFirstName())
        playerTwoTextView.setText(player2.getFirstName())
        Log.d(TAG, "setViews: " + player1.toString())
        Log.d(TAG, "setViews: " + player2.toString())
        Picasso.get()
                .load(PlayerUtil.getPlayerPhotoUrl(player1.getFirstName(), player1.getLastName()))
                .into(playerOneImage)
        Picasso.get()
                .load(PlayerUtil.getPlayerPhotoUrl(player2.getFirstName(), player2.getLastName()))
                .into(playerTwoImage)
        randomQuestion
        //        playerOneCardView.startAnimation(Animations.getFadeIn(playerOneCardView));
//        playerTwoCardView.startAnimation(Animations.getFadeIn(playerTwoCardView));
        playerOneStatTextView!!.text = "" + DecimalFormat("#.#").format(player1!!.getStat(randomQuestionPosition))
        playerTwoStatTextView!!.text = "" + DecimalFormat("#.#").format(player2!!.getStat(randomQuestionPosition))
    }

    private fun flipViews() {
//        Animation flip = AnimationUtils.loadAnimation(getActivity(), R.anim.card);
//        Animation flip_two = AnimationUtils.loadAnimation(getActivity(), R.anim.card);

//        playerOneCardView.startAnimation(flip);
//        playerOneCardView.setClickable(false);
//        playerTwoCardView.setClickable(false);
        val timer: CountDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                reloadPlayersAndViews()
            }
        }
        timer.start()

//        flip.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                playerOneTextView.setText("" + new DecimalFormat("#.#").format(player1.getStat(randomQuestionPosition)));
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                playerTwoCardView.startAnimation(flip_two);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        flip_two.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                double stat = player2.getStat(randomQuestionPosition);
//                playerTwoTextView.setText("" + new DecimalFormat("#.#").format(stat));
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//                handler.postDelayed(() -> {
//                    if (getFragmentManager().findFragmentByTag("game") != null &&
//                            getFragmentManager().findFragmentByTag("game").isVisible()) {
//                        playerOneCardView.startAnimation(Animations.getFadeOut(playerOneCardView));
//                        playerTwoCardView.startAnimation(Animations.getFadeOut(playerTwoCardView));
//                    }
//                }, 800);
//
//                handler2.postDelayed(() -> {
//                    if (getContext().getResources() != null) {
//                        reloadPlayersAndViews();
//
//                    }
//                }, 1500);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

    private fun setPlayer1CardView() {
        playerOneCardView!!.setOnClickListener { v: View? ->
            roundResults(1)
            flipViews()
        }
    }

    private fun setPlayer2CardView() {
        playerTwoCardView!!.setOnClickListener { v: View? ->
            roundResults(2)
            flipViews()
        }
    }

    private fun roundResults(i: Int) {
        if (GameJudger(player1, player2, i, randomQuestionPosition).isPlayerChoiceCorrect) {
            correct!!.startAnimation(Animations.getChecker(correct))
            playerCorrectGuesses++
        } else {
            incorrect!!.startAnimation(Animations.getChecker(incorrect))
            playerInCorrectGuesses++
        }
    }

    private fun reloadPlayersAndViews() {
        randomQuestion
        setRandomPlayers(playerAverageModels)
        setViews()
    }

    private val randomQuestion: Unit
        private get() {
            randomQuestionPosition = RandomNumberGenerator.getRandomNumber()
            Log.d(TAG, "getRandomQuestion: $randomQuestionPosition")
            displayQuestionTextView!!.text = BDLAppConstants.QUESTIONS_ARRAY[randomQuestionPosition]
        }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer!!.cancel()
    }

    companion object {
        //    private Handler handler;
        //    private Handler handler2;
        private const val TAG = "GameFragment"
        fun newInstance(): GameFragment {
            return GameFragment()
        }
    }
}