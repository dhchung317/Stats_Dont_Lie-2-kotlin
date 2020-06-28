package com.hyunki.statsdontlie2.view.fragments.game

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hyunki.statsdontlie2.R

class PlayerCardView(context: Context, attrs: AttributeSet? = null): ConstraintLayout(context, attrs) {
    init {
        inflate(context, R.layout.fragment_game, this)

        val playerCardView: CardView = findViewById(R.id.player_card_view)
        val playerImage: ImageView = findViewById(R.id.player_card_view_image)
        val playerTextView: TextView = findViewById(R.id.player_card_view_text_view)
        val playerStatTextView: TextView = findViewById(R.id.player_card_view_stat_text_view)
    }
}