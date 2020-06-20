package com.hyunki.statsdontlie2.view.fragments

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
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hyunki.statsdontlie2.Animations
import com.hyunki.statsdontlie2.controller.OnFragmentInteractionListener
import com.hyunki.statsdontlie2.R
import com.hyunki.statsdontlie2.constants.BDLAppConstants
import com.hyunki.statsdontlie2.model.NBAPlayer
import com.hyunki.statsdontlie2.utils.GameJudger
import com.hyunki.statsdontlie2.utils.PlayerUtil
import com.hyunki.statsdontlie2.utils.RandomNumberGenerator
import com.hyunki.statsdontlie2.view.NewViewModel
import com.hyunki.statsdontlie2.viewmodel.ViewModelProviderFactory
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import javax.inject.Inject

class GameFragment @Inject constructor(private val viewModelProviderFactory: ViewModelProviderFactory) : Fragment() {
    private lateinit var listener: OnFragmentInteractionListener

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

    private lateinit var NBAPlayer1: NBAPlayer
    private lateinit var NBAPlayer2: NBAPlayer

    private lateinit var viewModel: NewViewModel

    private var playerCorrectGuesses = 0
    private var playerInCorrectGuesses = 0
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var NBAPlayers: List<NBAPlayer>
    private var randomQuestionPosition = 0

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
        NBAPlayers = viewModel.getPlayerAverageModels()
        setCountDownTimer()
        setRandomPlayers(NBAPlayers)
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
                countDownView.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
//                playerOneCardView.clearAnimation();
//                playerTwoCardView.clearAnimation();
//                incorrect.clearAnimation();
//                correct.clearAnimation();
//                handler.removeCallbacksAndMessages(null);
//                handler2.removeCallbacksAndMessages(null);
                //todo listener to set results in viewmodel
                listener.setResultsDataFromGameFragment(playerCorrectGuesses, playerInCorrectGuesses)
                listener.displayResultFragment()
            }
        }
        countDownTimer.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(NewViewModel::class.java)
        //        viewModel.callBDLResponseClient();
    }

    //
    private fun observeViewModel() {
        NBAPlayers = viewModel.getPlayerAverageModels()
    }

    private fun setRandomPlayers(NBAPlayers: List<NBAPlayer>) {
        NBAPlayer1 = NBAPlayers[RandomNumberGenerator.randomNumber1]
        NBAPlayer2 = NBAPlayers[RandomNumberGenerator.randomNumber2]
    }

    private fun setViews() {
        playerOneTextView.text = NBAPlayer1.firstName
        playerTwoTextView.text = NBAPlayer2.firstName
        Log.d(TAG, "setViews: $NBAPlayer1")
        Log.d(TAG, "setViews: $NBAPlayer2")

        setPlayerOneImage()
        setPlayerTwoImage()

        randomQuestion
        //        playerOneCardView.startAnimation(Animations.getFadeIn(playerOneCardView));
//        playerTwoCardView.startAnimation(Animations.getFadeIn(playerTwoCardView));
        playerOneStatTextView.text = DecimalFormat("#.#").format(NBAPlayer1.getStat(randomQuestionPosition))
        playerTwoStatTextView.text = DecimalFormat("#.#").format(NBAPlayer2.getStat(randomQuestionPosition))
    }

    private fun setPlayerOneImage() {
        val bitmap = viewModel.getImageFromDatabase(NBAPlayer1.playerID.toInt())
        if(bitmap != null){
            playerOneImage.setImageBitmap(bitmap)
        }else{
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(NBAPlayer1.firstName, NBAPlayer1.lastName))
                    .into(playerOneImage)
        }
    }

    private fun setPlayerTwoImage() {
        val bitmap = viewModel.getImageFromDatabase(NBAPlayer2.playerID.toInt())
        if(bitmap != null){
            playerTwoImage.setImageBitmap(bitmap)
        }else{
            Picasso.get()
                    .load(PlayerUtil.getPlayerPhotoUrl(NBAPlayer1.firstName, NBAPlayer1.lastName))
                    .into(playerOneImage)
        }
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
        playerOneCardView.setOnClickListener { v: View? ->
            roundResults(1)
            flipViews()
        }
    }

    private fun setPlayer2CardView() {
        playerTwoCardView.setOnClickListener { v: View? ->
            roundResults(2)
            flipViews()
        }
    }

    private fun roundResults(i: Int) {
        if (GameJudger(NBAPlayer1, NBAPlayer2, i, randomQuestionPosition).isPlayerChoiceCorrect) {
            correct.startAnimation(Animations.getChecker(correct))
            playerCorrectGuesses++
        } else {
            incorrect.startAnimation(Animations.getChecker(incorrect))
            playerInCorrectGuesses++
        }
    }

    private fun reloadPlayersAndViews() {
        randomQuestion
        setRandomPlayers(NBAPlayers)
        setViews()
    }

    private val randomQuestion: Unit
        private get() {
            randomQuestionPosition = RandomNumberGenerator.randomNumber
            Log.d(TAG, "getRandomQuestion: $randomQuestionPosition")
            displayQuestionTextView.text = BDLAppConstants.QUESTIONS_ARRAY[randomQuestionPosition]
        }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer.cancel()
    }

    companion object {
        private const val TAG = "GameFragment"
    }
}