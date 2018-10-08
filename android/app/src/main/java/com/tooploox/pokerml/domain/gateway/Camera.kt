package com.tooploox.pokerml.domain.gateway

import android.graphics.Bitmap
import io.reactivex.Observable
import io.reactivex.Single

interface Camera {
    fun openCamera()
    fun startReceivingUpdates(): Observable<Bitmap>
    fun closeCamera()
    fun takePicture(): Single<Bitmap>
}
