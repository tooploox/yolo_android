package com.tooploox.pokerml.app.result

class ResultPresenterImpl(override val view: ResultView) : ResultPresenter {
    override fun release() = Unit

    override fun viewVisible() = Unit

    override fun viewHidden() = Unit
}
