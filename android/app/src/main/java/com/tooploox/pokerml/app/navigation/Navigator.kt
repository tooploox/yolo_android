package com.tooploox.pokerml.app.navigation

import android.content.Context
import com.tooploox.pokerml.domain.entity.Detection

interface Navigator {
    fun showResults(detection: List<Detection>, context: Context)
}
