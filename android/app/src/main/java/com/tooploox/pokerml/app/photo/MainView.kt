package com.tooploox.pokerml.app.photo

import com.tooploox.pokerml.app.base.MlView
import com.tooploox.pokerml.domain.entity.Detection

interface MainView : MlView {
    fun showResult(result: List<Detection>)
    fun showProgress()
    fun showError()
    fun resetView()
}
