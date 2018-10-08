package com.tooploox.pokerml.domain.usecase

import com.tooploox.pokerml.domain.entity.Detection
import com.tooploox.pokerml.domain.gateway.CameraApi
import com.tooploox.pokerml.domain.gateway.PokerImageProcessor
import com.tooploox.pokerml.domain.gateway.StoredBitmapProvider
import com.tooploox.pokerml.domain.usecase.base.Usecase
import io.reactivex.Single

class RecognizeImageUsecase(
        private val cameraApi: CameraApi,
        private val imageProcessor: PokerImageProcessor,
        private val bitmapStorage: StoredBitmapProvider
) : Usecase<Single<List<Detection>>> {
    override fun execute(): Single<List<Detection>> =
            cameraApi.currentCamera
                    ?.takePicture()
                    ?.doAfterSuccess(bitmapStorage::store)
                    ?.map(imageProcessor::process)
                    ?: Single.error(Exception("No camera attached"))
}
