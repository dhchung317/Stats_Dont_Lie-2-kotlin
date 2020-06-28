package com.hyunki.statsdontlie2.view.fragments.game.customviews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.hyunki.statsdontlie2.R

class PlayerCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    val playerCardView: CardView
    val playerImage: ImageView
    val playerNameTextView: TextView
    val playerStatTextView: TextView
    val attributes: TypedArray

    init {
        inflate(context, R.layout.card_view_player, this)

        attributes = context.obtainStyledAttributes(attrs, R.styleable.PlayerCardView)

        playerCardView = findViewById(R.id.player_card_view)
        playerImage = findViewById(R.id.player_card_view_image)
        playerNameTextView = findViewById(R.id.player_card_view_text_view)
        playerStatTextView = findViewById(R.id.player_card_view_stat_text_view)

//        playerImage.setImageDrawable(attributes.getDrawable(R.styleable.PlayerCardView_image))
//        playerNameTextView.text = attributes.getString(R.styleable.PlayerCardView_nameText)
//        playerStatTextView.text = attributes.getString(R.styleable.PlayerCardView_statText)

        playerCardView.setCardBackgroundColor(attributes.getColor(
                R.styleable.PlayerCardView_playerBackground, resources.getColor(R.color.colorRed)))

        attributes.recycle()

    }

//    fun setImageView(bitmap: Bitmap) {
//        playerImage.setImageBitmap(bitmap)
//    }
//
//    fun getImageView(): ImageView {
//        return playerImage
//    }
//
//    fun getNameView(): TextView {
//        return playerNameTextView
//    }
//
//    fun getStatView(): TextView {
//        return playerStatTextView
//    }

}