package com.tooploox.pokerml.app

import android.app.Activity
import android.content.Context
import com.tooploox.pokerml.app.gatewayimpl.BitmapStorageImpl
import com.tooploox.pokerml.app.gatewayimpl.FotoapparatCamera
import com.tooploox.pokerml.app.gatewayimpl.ModelFactoryImpl
import com.tooploox.pokerml.app.navigation.Navigator
import com.tooploox.pokerml.app.navigation.NavigatorImpl
import com.tooploox.pokerml.data.tensorflowprocessing.CameraApiImpl
import com.tooploox.pokerml.data.tensorflowprocessing.TensorflowImageProcessor
import com.tooploox.pokerml.domain.usecase.FinishCameraPreviewUsecase
import com.tooploox.pokerml.domain.usecase.EnableCameraPreviewUsecase
import com.tooploox.pokerml.domain.usecase.RecognizeImageUsecase
import io.fotoapparat.view.CameraView

class Injection(
        appContext: Context
) {

    private val cameraApi = CameraApiImpl()
    private val model = ModelFactoryImpl(appContext).fromAsset("coco.tflite")
    private val imageProcessor = TensorflowImageProcessor(model)
    private val bitmapStorage = BitmapStorageImpl()

    val openCameraUsecase = EnableCameraPreviewUsecase(cameraApi)
    val closeCameraUsecase = FinishCameraPreviewUsecase(cameraApi)
    val recognizeImageUsecase = RecognizeImageUsecase(cameraApi, imageProcessor, bitmapStorage)

    val navigator: Navigator = NavigatorImpl()

    fun attachCamera(activity: Activity, cameraView: CameraView) {
        cameraApi.currentCamera = FotoapparatCamera(activity, cameraView)
    }

}
