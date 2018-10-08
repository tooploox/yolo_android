package com.tooploox.pokerml.app.base

interface MlPresenter<out V : MlView> {
    val view: V

    fun release()

    fun viewVisible()

    fun viewHidden()
}
