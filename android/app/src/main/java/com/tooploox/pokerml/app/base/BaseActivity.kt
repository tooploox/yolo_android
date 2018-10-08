package com.tooploox.pokerml.app.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<P : MlPresenter<MlView>> : AppCompatActivity(), MlView {

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.viewVisible()
    }

    override fun onStop() {
        super.onStop()
        presenter.viewHidden()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.release()
    }

    abstract fun createPresenter(): P
}
