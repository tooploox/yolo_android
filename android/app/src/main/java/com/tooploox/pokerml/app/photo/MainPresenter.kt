package com.tooploox.pokerml.app.photo

import com.tooploox.pokerml.app.base.MlPresenter

interface MainPresenter : MlPresenter<MainView> {
    fun takePhoto()
    fun retry()
    fun startPreview()
}
