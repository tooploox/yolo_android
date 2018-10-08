package com.tooploox.pokerml.app.photo

import com.tooploox.pokerml.app.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(
        override val view: MainView,
        private val injection: Injection
) : MainPresenter {

    var isProcessing = false
    var disposable: Disposable? = null

    override fun viewVisible() = Unit

    override fun onPermissionsGranted() {
        injection.openCameraUsecase.execute().subscribe()
    }

    override fun takePhotoClicked() {
        if (!isProcessing) {
            isProcessing = true
            view.showProgress()
            disposable = injection.recognizeImageUsecase.execute()
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

    override fun retryClicked() {
        view.resetView()
    }

    override fun viewHidden() {
        disposable?.dispose()
        injection.closeCameraUsecase.execute().subscribe()
    }

    override fun release() = Unit
}
