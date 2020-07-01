package com.hyunki.statsdontlie2.view.fragments.game.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.hyunki.statsdontlie2.R
import kotlin.properties.Delegates.observable

class PlayerCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {
    private val attributes: TypedArray

    val playerImage: ImageView

    private val playerCardView: CardView by lazy { findViewById<CardView>(R.id.player_card_view) }
    private val playerNameTextView: TextView by lazy { findViewById<TextView>(R.id.player_card_view_text_view) }
    val playerStatTextView: TextView by lazy { findViewById<TextView>(R.id.player_card_view_stat_text_view) }

    var playerName: String
            by observable(initialValue = "") {
                _, _, newValue ->
                playerNameTextView.text = newValue
    }

    var playerNameLength: Int by observable(initialValue = 0) { _, _, newValue ->
        if(newValue > 8) {
            playerNameTextView.textSize = 34f
        }else{
            playerNameTextView.textSize = 36f
        }
    }
    var playerStat: String by observable(initialValue = "") { _, _, newValue ->
        playerStatTextView.text = newValue
    }
    var playerStatVisibility: Boolean by observable<Boolean>(initialValue = false) { _, _, newValue ->
        when (newValue) {
            true -> playerStatTextView.visibility = View.VISIBLE
            else -> playerStatTextView.visibility = View.INVISIBLE
        }
    }

    init {
        inflate(context, R.layout.card_view_player, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.PlayerCardView)
        playerImage = findViewById(R.id.player_card_view_image)
        playerCardView.setCardBackgroundColor(attributes.getColor(
                R.styleable.PlayerCardView_playerBackground, ContextCompat.getColor(context,R.color.colorRed)))
        attributes.recycle()
    }
}