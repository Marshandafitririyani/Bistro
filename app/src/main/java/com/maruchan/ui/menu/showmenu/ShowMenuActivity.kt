package com.maruchan.ui.menu.showmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityRamenDiningBinding
import com.maruchan.bistro.databinding.ActivityShowMenuBinding
import com.maruchan.ui.menu.ramen.RamenDiningViewModel
import dagger.hilt.android.AndroidEntryPoint

class ShowMenuActivity : BaseActivity<ActivityShowMenuBinding, CoreViewModel>(R.layout.activity_show_menu) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}