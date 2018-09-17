package com.tooploox.pokerml.data.tensorflowprocessing

import com.tooploox.pokerml.domain.gateway.Camera
import com.tooploox.pokerml.domain.gateway.CameraApi

class CameraApiImpl : CameraApi {
    override var currentCamera: Camera? = null
}
