package com.hyunki.statsdontlie2.view.fragments.game

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hyunki.statsdontlie2.R

class GameFragmentView(context: Context, attrs: AttributeSet? = null): ConstraintLayout(context, attrs) {
    init {
        inflate(context, R.layout.fragment_game, this)

        val playerOneCardView: CardView
        val playerTwoCardView: CardView

        val playerOneImage: ImageView
        val playerTwoImage: ImageView

        val playerOneTextView: TextView
        val playerTwoTextView: TextView

        val playerOneStatTextView: TextView
        val playerTwoStatTextView: TextView

        val countDownView: TextView
        val displayQuestionTextView: TextView

        val correct: ImageView
        val incorrect: ImageView
    }


}