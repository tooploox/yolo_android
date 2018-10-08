package com.tooploox.pokerml.app.photo

import com.tooploox.pokerml.app.base.MlPresenter

interface MainPresenter : MlPresenter<MainView> {
    fun takePhotoClicked()
    fun retryClicked()
    fun onPermissionsGranted()
}
