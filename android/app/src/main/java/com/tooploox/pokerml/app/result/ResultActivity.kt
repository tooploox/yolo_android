package com.tooploox.pokerml.app.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.tooploox.pokerml.R
import com.tooploox.pokerml.app.gatewayimpl.BitmapStorageImpl
import com.tooploox.pokerml.app.base.BaseActivity
import com.tooploox.pokerml.app.view.DetectionsAdapter
import com.tooploox.pokerml.domain.entity.Detection
import kotlinx.android.synthetic.main.activity_result.*
import java.io.Serializable

class ResultActivity : BaseActivity<ResultPresenter>(), ResultView {

    companion object {
        const val EXTRA_KEY = "extra_detections"
        private const val COLUMN_COUNT = 4

        fun getStartIntent(detection: List<Detection>, context: Context): Intent =
                Intent(context, ResultActivity::class.java).apply {
                    putExtra(EXTRA_KEY, detection as Serializable)
                }
    }

    override fun createPresenter(): ResultPresenter =
            ResultPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val results = intent.extras.get(EXTRA_KEY) as List<Detection>

        ibBack.setOnClickListener { onBackPressed() }

        ivSourceImage.setImageBitmap(BitmapStorageImpl().retreive())
        rvCards.adapter = DetectionsAdapter().apply { detections = results }
        rvCards.layoutManager = GridLayoutManager(this, COLUMN_COUNT)
    }
}
