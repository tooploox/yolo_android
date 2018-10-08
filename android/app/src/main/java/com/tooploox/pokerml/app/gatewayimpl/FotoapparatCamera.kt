package com.tooploox.pokerml.app.gatewayimpl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import com.tooploox.pokerml.domain.gateway.Camera
import io.fotoapparat.Fotoapparat
import io.fotoapparat.view.CameraView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class FotoapparatCamera(
        context: Context,
        view: CameraView
) : Camera {

    private val fotoapparat = Fotoapparat(
            context,
            view
    )

    private var disposable: Disposable? = null

    override fun openCamera() {
        fotoapparat.start()
    }

    override fun takePicture(): Single<Bitmap> =
            Single.create { emitter ->
                fotoapparat.takePicture().toBitmap()
                        .whenAvailable {
                            if (it != null) {
                                val rawBitmap = it.bitmap
                                val rotationMatrix = Matrix().apply { postRotate(-it.rotationDegrees.toFloat()) }
                                val rotatedBitmap = Bitmap.createBitmap(
                                        rawBitmap,
                                        0,
                                        0,
                                        rawBitmap.width,
                                        rawBitmap.height,
                                        rotationMatrix,
                                        true
                                )
                                emitter.onSuccess(rotatedBitmap)
                            } else {
                                emitter.onError(Throwable("Empty photo result"))
                            }
                        }
            }

    override fun startReceivingUpdates(): Observable<Bitmap> = Observable.create { emitter ->
        disposable = Observable.interval(2, TimeUnit.SECONDS)
                .map { _ ->
                    fotoapparat.takePicture()
                            .toBitmap()
                            .whenAvailable { bitmapPhoto -> emitter.onNext(bitmapPhoto!!.bitmap) }
                }
                .subscribe()
    }

    override fun closeCamera() {
        disposable?.dispose()
        fotoapparat.stop()
    }
}
