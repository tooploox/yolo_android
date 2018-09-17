package com.tooploox.pokerml.app.navigation

import android.content.Context
import com.tooploox.pokerml.app.result.ResultActivity
import com.tooploox.pokerml.domain.entity.Detection

class NavigatorImpl : Navigator {
    override fun showResults(detection: List<Detection>, context: Context) {
        context.startActivity(ResultActivity.getStartIntent(detection, context))
    }
}
