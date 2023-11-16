package com.maruchan.ui.menu.dining

import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityDiningBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiningActivity : BaseActivity<ActivityDiningBinding, CoreViewModel>(R.layout.activity_dining) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}