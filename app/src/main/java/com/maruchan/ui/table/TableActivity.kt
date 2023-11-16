package com.maruchan.ui.table

import android.os.Bundle
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.extension.tos
import com.maruchan.bistro.R
import com.maruchan.bistro.base.BaseActivity
import com.maruchan.bistro.databinding.ActivityTableBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TableActivity : BaseActivity<ActivityTableBinding, CoreViewModel>(R.layout.activity_table) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.orderATab.setOnClickListener {
           tos("Success")

        }
    }
}