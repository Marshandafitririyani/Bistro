package com.maruchan.ui.menu.bruch

import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityBrunchDiningctivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrunchDiningActivity :
    BaseActivity<ActivityBrunchDiningctivityBinding, CoreViewModel>(R.layout.activity_brunch_diningctivity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}