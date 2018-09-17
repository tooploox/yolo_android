package com.tooploox.pokerml.app.photo

import com.tooploox.pokerml.app.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(
        override val view: MainView,
        private val injection: Injection
) : MainPresenter {

    var isProcessing = false

    override fun viewVisible() = Unit

    override fun startPreview() {
        injection.openCameraUsecase.execute().subscribe()
    }

    override fun takePhoto() {
        if (!isProcessing) {
            isProcessing = true
            view.showProgress()
            injection.recognizeImageUsecase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                isProcessing = false
                                if (it.isNotEmpty()) {
                                    view.showResult(it)
                                } else {
                                    view.showError()
                                }
                            },
                            {
                                isProcessing = false
                                throw it
                            }
                    )
        }
    }

    override fun retry() {
        view.resetView()
    }

    override fun viewHidden() {
        injection.closeCameraUsecase.execute().subscribe()
    }

    override fun release() = Unit
}
