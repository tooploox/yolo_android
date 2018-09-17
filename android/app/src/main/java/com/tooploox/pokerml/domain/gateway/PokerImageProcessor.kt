package com.tooploox.pokerml.domain.gateway

import android.graphics.Bitmap
import com.tooploox.pokerml.domain.entity.Detection

interface PokerImageProcessor {
    fun process(bitmap: Bitmap): List<Detection>
}
