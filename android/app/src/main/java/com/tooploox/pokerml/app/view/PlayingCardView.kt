package com.tooploox.pokerml.app.view

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.tooploox.pokerml.R
import com.tooploox.pokerml.data.tensorflowprocessing.TensorflowImageProcessor
import com.tooploox.pokerml.domain.entity.Card
import com.tooploox.pokerml.domain.entity.Detection
import com.tooploox.pokerml.domain.entity.Rank
import com.tooploox.pokerml.domain.entity.Suit
import kotlinx.android.synthetic.main.view_playing_card.view.*
import kotlin.math.round

class PlayingCardView(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_playing_card, this)
    }

    fun displayDetection(detection: Detection) {
        val accuracyApprox: Int = round(detection.probability * 100).toInt()
        ivCard.setBackgroundResource(AssetMap.cardToDrawable.getValue(detection.card))
        pbAccuracy.progress = accuracyApprox
        tvAccuracy.text = context.getString(R.string.recognition_accuracy, accuracyApprox)

        val oneThirdOfRange = (1 - TensorflowImageProcessor.DETECTION_THRESHOLD) / 3.0

        (pbAccuracy.progressDrawable as LayerDrawable).findDrawableByLayerId(android.R.id.progress)
                .setTint(ContextCompat.getColor(context, when (detection.probability) {
                    in (1 - oneThirdOfRange)..1.0 -> R.color.recognition_good
                    in (1 - (2 * oneThirdOfRange))..(1 - oneThirdOfRange) -> R.color.recognition_satisfactory
                    else -> R.color.recognition_borderline
                }))
    }
}

object AssetMap {
    val cardToDrawable = mapOf(
            Card(Rank.TEN, Suit.CLUBS) to R.drawable.ten_of_clubs,
            Card(Rank.TEN, Suit.DIAMONDS) to R.drawable.ten_of_diamonds,
            Card(Rank.TEN, Suit.HEARTS) to R.drawable.ten_of_hearts,
            Card(Rank.TEN, Suit.SPADES) to R.drawable.ten_of_spades,
            Card(Rank.TWO, Suit.CLUBS) to R.drawable.two_of_clubs,
            Card(Rank.TWO, Suit.DIAMONDS) to R.drawable.two_of_diamonds,
            Card(Rank.TWO, Suit.HEARTS) to R.drawable.two_of_hearts,
            Card(Rank.TWO, Suit.SPADES) to R.drawable.two_of_spades,
            Card(Rank.THREE, Suit.CLUBS) to R.drawable.three_of_clubs,
            Card(Rank.THREE, Suit.DIAMONDS) to R.drawable.three_of_diamonds,
            Card(Rank.THREE, Suit.HEARTS) to R.drawable.three_of_hearts,
            Card(Rank.THREE, Suit.SPADES) to R.drawable.three_of_spades,
            Card(Rank.FOUR, Suit.CLUBS) to R.drawable.four_of_clubs,
            Card(Rank.FOUR, Suit.DIAMONDS) to R.drawable.four_of_diamonds,
            Card(Rank.FOUR, Suit.HEARTS) to R.drawable.four_of_hearts,
            Card(Rank.FOUR, Suit.SPADES) to R.drawable.four_of_spades,
            Card(Rank.FIVE, Suit.CLUBS) to R.drawable.five_of_clubs,
            Card(Rank.FIVE, Suit.DIAMONDS) to R.drawable.five_of_diamonds,
            Card(Rank.FIVE, Suit.HEARTS) to R.drawable.five_of_hearts,
            Card(Rank.FIVE, Suit.SPADES) to R.drawable.five_of_spades,
            Card(Rank.SIX, Suit.CLUBS) to R.drawable.six_of_clubs,
            Card(Rank.SIX, Suit.DIAMONDS) to R.drawable.six_of_diamonds,
            Card(Rank.SIX, Suit.HEARTS) to R.drawable.six_of_hearts,
            Card(Rank.SIX, Suit.SPADES) to R.drawable.six_of_spades,
            Card(Rank.SEVEN, Suit.CLUBS) to R.drawable.seven_of_clubs,
            Card(Rank.SEVEN, Suit.DIAMONDS) to R.drawable.seven_of_diamonds,
            Card(Rank.SEVEN, Suit.HEARTS) to R.drawable.seven_of_hearts,
            Card(Rank.SEVEN, Suit.SPADES) to R.drawable.seven_of_spades,
            Card(Rank.EIGHT, Suit.CLUBS) to R.drawable.eight_of_clubs,
            Card(Rank.EIGHT, Suit.DIAMONDS) to R.drawable.eight_of_diamonds,
            Card(Rank.EIGHT, Suit.HEARTS) to R.drawable.eight_of_hearts,
            Card(Rank.EIGHT, Suit.SPADES) to R.drawable.eight_of_spades,
            Card(Rank.NINE, Suit.CLUBS) to R.drawable.nine_of_clubs,
            Card(Rank.NINE, Suit.DIAMONDS) to R.drawable.nine_of_diamonds,
            Card(Rank.NINE, Suit.HEARTS) to R.drawable.nine_of_hearts,
            Card(Rank.NINE, Suit.SPADES) to R.drawable.nine_of_spades,
            Card(Rank.ACE, Suit.CLUBS) to R.drawable.ace_of_clubs,
            Card(Rank.ACE, Suit.DIAMONDS) to R.drawable.ace_of_diamonds,
            Card(Rank.ACE, Suit.HEARTS) to R.drawable.ace_of_hearts,
            Card(Rank.ACE, Suit.SPADES) to R.drawable.ace_of_spades,
            Card(Rank.JACK, Suit.CLUBS) to R.drawable.jack_of_clubs,
            Card(Rank.JACK, Suit.DIAMONDS) to R.drawable.jack_of_diamonds,
            Card(Rank.JACK, Suit.HEARTS) to R.drawable.jack_of_hearts,
            Card(Rank.JACK, Suit.SPADES) to R.drawable.jack_of_spades,
            Card(Rank.KING, Suit.CLUBS) to R.drawable.king_of_clubs,
            Card(Rank.KING, Suit.DIAMONDS) to R.drawable.king_of_diamonds,
            Card(Rank.KING, Suit.HEARTS) to R.drawable.king_of_hearts,
            Card(Rank.KING, Suit.SPADES) to R.drawable.king_of_spades,
            Card(Rank.QUEEN, Suit.CLUBS) to R.drawable.queen_of_clubs,
            Card(Rank.QUEEN, Suit.DIAMONDS) to R.drawable.queen_of_diamonds,
            Card(Rank.QUEEN, Suit.HEARTS) to R.drawable.queen_of_hearts,
            Card(Rank.QUEEN, Suit.SPADES) to R.drawable.queen_of_spades
    )
}
