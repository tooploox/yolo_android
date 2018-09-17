package com.tooploox.pokerml.domain.gateway

import android.graphics.Bitmap

interface StoredBitmapProvider {
    fun store(bitmap: Bitmap)
    fun retreive(): Bitmap
}
