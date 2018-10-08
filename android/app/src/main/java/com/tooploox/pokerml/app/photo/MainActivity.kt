package com.tooploox.pokerml.app.photo

import android.Manifest
import android.os.Bundle
import android.view.View
import com.tooploox.pokerml.R
import com.tooploox.pokerml.app.base.BaseActivity
import com.tooploox.pokerml.app.injection
import com.tooploox.pokerml.domain.entity.Detection
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun createPresenter(): MainPresenter = MainPresenterImpl(this, injection)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        injection.attachCamera(this, cvPreview)
        super.onCreate(savedInstanceState)
        cvPreview.setOnClickListener { presenter.takePhotoClicked() }
        btnTryAgain.setOnClickListener { presenter.retryClicked() }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        presenter.onPermissionsGranted()
    }

    private fun checkPermissions() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            presenter.onPermissionsGranted()
            return
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_ask), 123, *perms)
        }
    }

    override fun showResult(result: List<Detection>) {
        injection.navigator.showResults(result, this)
        resetView()
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
