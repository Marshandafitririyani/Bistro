package com.maruchan.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivitySplashBinding
import com.maruchan.ui.home.HomeActivity
import com.maruchan.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClick()
    }
    private  fun initClick(){
        binding.btnNext.setOnClickListener {
            openActivity<LoginActivity>()
        }
    }
}