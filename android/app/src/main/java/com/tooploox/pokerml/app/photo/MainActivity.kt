package com.tooploox.pokerml.app.photo

import android.Manifest
import android.os.Bundle
import android.view.View
import com.tooploox.pokerml.R
import com.tooploox.pokerml.app.base.BaseActivity
import com.tooploox.pokerml.app.injection
import com.tooploox.pokerml.app.navigation.NavigatorImpl
import com.tooploox.pokerml.domain.entity.Detection
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun createPresenter(): MainPresenter = MainPresenterImpl(this, injection)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        injection.attachCamera(this, cvPreview)
        super.onCreate(savedInstanceState)
        requestPermissions()
        cvPreview.setOnClickListener { presenter.takePhoto() }
        btnTryAgain.setOnClickListener { presenter.retry() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        presenter.startPreview()
    }

    private fun requestPermissions() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            presenter.startPreview()
            return
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_ask), 123, *perms)
        }
    }

    override fun showResult(result: List<Detection>) {
        NavigatorImpl().showResults(result, this)
        finish()
    }

    override fun showProgress() {
        tvPrompt.visibility = View.GONE
        gRecognizingProgress.visibility = View.VISIBLE
        gRecognizingFailed.visibility = View.GONE
    }

    override fun showError() {
        tvPrompt.visibility = View.GONE
        gRecognizingFailed.visibility = View.VISIBLE
        gRecognizingProgress.visibility = View.GONE
    }

    override fun resetView() {
        tvPrompt.visibility = View.VISIBLE
        gRecognizingFailed.visibility = View.GONE
        gRecognizingProgress.visibility = View.GONE
    }
}
