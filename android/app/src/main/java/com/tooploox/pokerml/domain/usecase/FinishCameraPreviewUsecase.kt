package com.tooploox.pokerml.domain.usecase

import com.tooploox.pokerml.domain.gateway.CameraApi
import com.tooploox.pokerml.domain.usecase.base.Usecase
import io.reactivex.Completable

class FinishCameraPreviewUsecase(
        private val cameraApi: CameraApi
) : Usecase<Completable> {
    override fun execute(): Completable = Completable.create { emitter ->
        cameraApi.currentCamera?.let {
            it.closeCamera()
            emitter.onComplete()
        }
                ?: emitter.onError(Exception("No camera attached"))
    }
}
