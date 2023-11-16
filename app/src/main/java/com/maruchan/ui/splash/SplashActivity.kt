package com.maruchan.ui.splash

import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivitySplashBinding
import com.maruchan.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, CoreViewModel>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClick()
    }

    private fun initClick() {
        binding.btnNext.setOnClickListener {
            openActivity<LoginActivity>()
        }
    }
}