package com.maruchan.ui.screen

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityScreenBinding
import com.maruchan.ui.home.HomeActivity
import com.maruchan.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class ScreenActivity : BaseActivity<ActivityScreenBinding, ScreenViewModel>(R.layout.activity_screen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.splash {
            if (it) {
                openActivity<HomeActivity>()
            } else {
                openActivity<SplashActivity>()
            }
            finish()
        }


    }
}