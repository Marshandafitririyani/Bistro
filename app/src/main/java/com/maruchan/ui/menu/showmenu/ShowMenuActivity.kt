package com.maruchan.ui.menu.showmenu

import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityShowMenuBinding

class ShowMenuActivity :
    BaseActivity<ActivityShowMenuBinding, CoreViewModel>(R.layout.activity_show_menu) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}